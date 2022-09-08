package br.com.ramondev.hotelservice.model.service;

import java.net.URI;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.FichaCadastro;
import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Produto;
import br.com.ramondev.hotelservice.model.dto.ConsumoDTO;
import br.com.ramondev.hotelservice.model.dto.ProdutoDTO;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.exception.RegistrationFormNotFoundException;
import br.com.ramondev.hotelservice.model.repository.FichaCadastroRepository;
import br.com.ramondev.hotelservice.model.repository.HospedeRepository;
import br.com.ramondev.hotelservice.model.repository.ProdutoRepository;

@Service
public class ConsumoService {

  @Autowired
  private HospedeRepository hospedeRepository;

  @Autowired
  private FichaCadastroRepository fichaCadastroRepository;

  @Autowired
  private ProdutoRepository produtoRepository;

  public List<ProdutoDTO> consultarConsumo(String cpfHospede) {
    Hospede hospede = hospedeRepository.findByCpfHospede(cpfHospede.replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema");
    }

    List<ProdutoDTO> consumo = new ArrayList<>();

    for (Produto p : fichaCadastroRepository.findByHospede(hospede).getConsumo()) {
      ProdutoDTO dto = new ProdutoDTO();
      dto.setNomeProduto(p.getNomeProduto());
      dto.setPreco(p.getPreco());
      dto.setQuantidade(p.getQuantidade());
      consumo.add(dto);
    }

    return consumo;
  }

  @Transactional
  public ResponseEntity<Object> cadastrarConsumo(ConsumoDTO consumoDTO) {
    Hospede hospede = hospedeRepository.findByCpfHospede(consumoDTO.getCpfHospede().replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema");
    }

    FichaCadastro fichaCadastro = fichaCadastroRepository.findByHospede(hospede);

    // o hospede ja deve possuir uma ficha de cadastro no sistema do hotel
    if (fichaCadastro == null) {
      throw new RegistrationFormNotFoundException(
          "Hospede nao possui ficha de cadastro no hotel. Verifique se o Check In foi realizado.");
    }

    FichaCadastro fichaCriada = new FichaCadastro();
    fichaCriada = fichaCadastro;

    List<Produto> produtos = fichaCadastro.getConsumo();

    for (ProdutoDTO produtoDTO : consumoDTO.getConsumo()) {
      // como a procura é feita por nome, vamos normalizar a string
      // para obter uma busca mais segura
      Produto produtoProcurado = produtoRepository.findByNomeProduto(normalizarString(produtoDTO.getNomeProduto()));

      if (produtoProcurado != null) {
        produtoProcurado.setQuantidade(produtoProcurado.getQuantidade() + produtoDTO.getQuantidade());
        produtoProcurado = produtoRepository.save(produtoProcurado);
      } else {
        Produto produto = new Produto(normalizarString(produtoDTO.getNomeProduto()), produtoDTO.getPreco(),
            produtoDTO.getQuantidade(),
            hospede);
        produto = produtoRepository.save(produto);
        produtos.add(produto);
      }
    }

    fichaCriada.setConsumo(produtos);
    fichaCriada = fichaCadastroRepository.save(fichaCriada);

    URI locationConsumo = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpfHospede}")
        .buildAndExpand(fichaCriada.getHospede().getCpfHospede()).toUri();

    return ResponseEntity.created(locationConsumo).build();
  }

  public String normalizarString(String string) {
    if (string != null)
      // retira caracteres especiais da string e aplica capitalização nas letras
      return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
    else
      return null;
  }
}