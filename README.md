# fast-food
Sistema de controle de pedidos que permite aos clientes selecionar e fazer os pedidos sem interagir com o atendente e integração com as areas da lanchonete como cozinha e o balcão para recebimento.

## Tecnologias
* Kotlin
* Database Migration
* Spring Boot Data Jpa
* Spring Boot Web
* Docker
* Docker Compose

## Banco de Dados
* PostgreSQL

### Comandos para iniciar a aplicação
Iniciar a aplicação, a porta da aplicação é 8080(http://localhost:8080)
```bash
docker compose up -d
```
Parar a aplicação
```bash
docker compose down
```

## Endpoints
### Criar checkout do pedido
```bash 
curl --request POST \
  --url http://localhost:8080/pedidos/checkout \
  --header 'Content-Type: application/json' \
  --data '{
	"id_cliente":"13907e60-0971-4856-93ba-7e184916e0e6",
	"itens": [
		{
			"id_produto":"abf2da35-a638-4db6-a553-00a9dfd76e4e",
			"quantidade": 1,
			"preco": 39.99
		},
		{
			"id_produto":"a2a22642-e7e1-4cae-9b18-db2f7c0f3f42",
			"quantidade": 1,
			"preco": 9.99
		},
		{
			"id_produto":"8e2053d7-d93a-4b4a-ade6-f53c4e043730",
			"quantidade": 2,
			"preco": 20.00
		}
	]
}'
```

### Listar Pedido por ID acompanhado o status
```bash 
curl --request GET \
  --url http://localhost:8080/pedidos/eee0b41a-f582-4288-8548-9292ac95f2ec
```

### Lista de pedidos ordenados por recebimento e por status
```bash 
curl --request GET \
  --url http://localhost:8080/pedidos
```

### Mudar o status do pedido para Finalizado
```bash
curl --request PUT \
  --url http://localhost:8080/pedidos/mudar-status/confirmar-entrega/eee0b41a-f582-4288-8548-9292ac95f2ec
```

## Orquestração Saga do pagamento e producao do pedido
Segue abaixo o fluxo de execução da orquestração da saga do pagamento e produção do pedido:
1. O sistema de pedido realiza uma chamada sincrona ao sistema de pagamento para gerar um QRCode.
2. O sistema de pagamento recebe uma notificação de pagamento e envia um evento de pagamento para a fila "notificacao-pagamento".  
3. O sistema de pedido recebe o evento de pagamento, atualiza o status do pedido para recebido e envia um evento de produção para a fila "notificacao-pedido".
4. O sistema de produção recebe o evento de produção, atualiza o status do pedido para em produção e após terminar o pedido envia um evento de pedido pronto para a fila "notificacao-pedido-status".
5. O sistema de pedido recebe o evento de pedido pronto e envia para o cliente

Segue abaixo o fluxo de execução da orquestração da saga do pagamento e produção do pedido em caso de erro:
1. Se houver algum erro no pagamento o sistema de pagamento envia um evento de pagamento falho para a fila "notificacao-pagamento-error".
2. O sistema de pedido recebe o evento de pagamento falho e atualiza o status do pedido para cancelado.
3. Se houver algum erro na produção o sistema de produção envia um evento de produção falha para a fila "notificacao-pedido-status-error".
4. O sistema de pedido recebe o evento de produção falha e atualiza o status do pedido para cancelado.

Imagem do fluxo de execução da orquestração da saga do pagamento e produção do pedido.
![Orquestração Saga do pagamento e producao do pedido](https://github.com/rafaelgil/fast-food/assets/2104773/7c048900-2b8f-4267-9676-0563fc37a743)

### Saga escolhido
Optamos pela implementação do padrão Saga Coreografada devido à sua simplicidade de implementação, especialmente no fluxo de pagamento e produção que envolve poucos serviços. A escolha por não ter um componente centralizado que coordena a comunicação entre os serviços foi um fator decisivo, uma vez que, se esse sistema falhar, toda a integração com os diversos sistemas será afetada. A documentação apresentada contribui para um melhor entendimento desse fluxo. Além disso, os sistemas apresentam um acoplamento fraco, já que a comunicação entre eles ocorre por meio de eventos.

### Links
Pasta com o vídeo da apresentação, relatório ZAP e RIPD 
https://drive.google.com/drive/folders/1IrsVaKrz93rdKI3T6E3OLx8eRECKWt7x?usp=drive_link
Apresentação SAGA
https://drive.google.com/file/d/1NcjawOmb47IC9eSkRRBtV_rga9IUSXyY/view?usp=drive_link
ZAP-Pagamento
https://drive.google.com/file/d/1tgkte2XecRmntpPenlzVvN7vqlF20a0P/view?usp=drive_link
ZAP-Pagamento-Solução
https://drive.google.com/file/d/13NfeJFvibJcdy9F2GYzx94nbOA7JqwWz/view?usp=drive_link
ZAP-Produto
https://drive.google.com/file/d/1UQ9hMKu4khK09aT190tL6F7l0zr9mlTX/view?usp=drive_link
ZAP-Producao
https://drive.google.com/file/d/1H27A1b4NuqgGeKJhjUqrt1N8m8uvEpAd/view?usp=drive_link
RIPD do sistema
https://drive.google.com/file/d/1LILr3iFeZ2ETd2DpphMsA-Hc0QSXflaq/view?usp=drive_link
