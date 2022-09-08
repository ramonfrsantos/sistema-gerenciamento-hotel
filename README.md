# sistema-gerenciamento-hotel
Aplicação para gerenciar um serviço de hotel.

##
Documentação: Swagger "/swagger-ui.html"
Funcionalidades:
- Banco de dados MySQL, hospedado em "http://freesqldatabase.com/";
- Cadastro de apartamentos (PRESIDENCIAL e PADRÃO) : "/apartamentos" [POST]
  - Preços da diária:
    - PADRÃO: R$ 125,00 (segunda a sexta) e R$ 150,00 (finais de smeana)
    - PRESIDENCIAL: R$ 360,00 (segunda a sexta) e R$ 450,00 (finais de semana)
- Cadastro de hóspedes: "/hospedes" [POST]
- Consultar hóspedes antigos: "/hospedes/antigos" [GET]
- Fazer reserva: "/reservas" [POST]
- Consultar reserva pelo CPF do hospede: "/reserva/{cpfHospede}" [GET]
- Fazer Check In: "fichas-cadastro/check-in" [POST]
- Registrar consumo no hotel: "/consumo" [POST]
- Consultar produtos consumidos pelo hóspede: "/consumo/{cpfHospede}" [GET]
- Fazer Check Out: "/fichas-cadastro/check-out" [PUT]
- Testes unitários validando o fluxo correto;

A aplicação irá mostrar no console as informações mais relevantes de acordo com a determinação das regras de negócio:
- Se o hóspede desejou vaga na garagem;
  - Caso sim, é cobrada uma taxa adicional de R$ 15,00 de segunda a sexta e R$ 20,00 aos finais de semana;
- Se o Check Out foi feito após as 16h30;
  - Caso sim, é cobrada uma diária adicional;
  
Mais detalhes da documentação com algumas outras funcionalidades, métricas, etc., é possível acessar através do "/actuator";
##
Assinador por: Ramon Santos