# Destino Boladão

Este é um trabalho bimestral proposto na disciplina Estrutura de Dados II, do curso Análise e Desenvolvimento de Sistemas do IFTO - *Campus* Araguaína.

# Conceito

A princípio, criar uma aplicação CLI utilizando Java que irá calcular qual é a melhor rota entre duas cidades da Região Metropolitana de Gurupi (instituída pela LEI COMPLEMENTAR Nº 172, DE 11 DE FEVEREIRO DE 2026) utilizando conceitos básicos de Grafos.

# Restrições

Não é permitido o uso de bibliotecas de grafos ou quaisquer outras estruturas de dados prontas para este fim. A única exceção é o uso de ArrayLists (Java).

# Integrantes

- Alexandre William
- Jackson Alves dos Santos
- Kamilly Silva
- Lidia Cruz de Araújo
- Paulo Ricardo Rodrigues Silva

# Arquitetura (Planejamento)

A aplicação é composta por três camadas: CLI, Camada de Serviço e Camada de Dados.

## CLI

É a Interface do Usuário, que vai obter as entradas, validá-las e retornar os resultados para o usuário. Ela é consumida exclusivamente via terminal.

## Camada de Serviço

É a camada responsável por fazer o processamento das rotas solicitadas pelo usuário e retornar os resultados. O processamento é realizado com base no Grafo em memória.

## Camada de Dados

É a camada responsável por comunicar com a base de dados, em JSON. Ou seja, realizar todas as consultas necessárias para montar grafo em memória.

Não serão necessárias realizar inserções ou qualquer tipo de modificações, pois as informações serão estáticas, ao menos a princípio.

# Definições

## Fontes de dados

### Para trechos em concessão

> Só existem pedágios em trechos concedidos (privatizados).
> 

Para checagem de Postos da PRF, Pedágios, acessar em: [https://www.ecoviasaraguaia.com.br/condicoes-da-via](https://www.ecoviasaraguaia.com.br/condicoes-da-via)

Para checagem de trechos em obras, acessar em: [Duplicação da BR-153/GO/TO - EcoRodovias](https://www.ecoviasaraguaia.com.br/servicos/duplicacao-br-153-go-to)

### Lei Complementar nº 172

- Nome das cidades.

### Mapa Multimodal do Tocantins - DNIT

- Nome das Rodovias;
- Presença de Asfalto;
- Postos da PRF; e
- Tipo de via.

### Wase

- Distância entre cidades; e
- Presença de buracos.

### OpenStreetMap

- Limite de velocidade.

### Mapa de Manutenção Rodoviária

- Manutenção de Rodovias Federais (BRs).

### Portal de Notícias do AGETO

- Manutenção de Rodovias Estaduais (TOs).

### Pesquisa CNT de Rodovias

- Estado geral da rodovia.

## Entidades e atributos

| Cidade | Rodovia (segmento) |
| --- | --- |
| id | id |
| nome | nome |
|  | condição geral |
|  | distância entre vértices (cidades) |
|  | presença de asfalto |
|  | presença de buracos |
|  | tipo de via (simples, dupla, etc.) |
|  | presença de pedágios |
|  | está em obras |
|  | velocidade média permitida |
|  | postos da prf |

## Utilização dos atributos

### Peso 1 (Distância entre os destinos)

- Distância entre vértices.

### Peso 2 (Tempo de viagem)

- Distância entre vértices;
- Presença de asfalto;
- Presença de buracos;
- Tipo de via
- Presença de Pedágios;
- Está em obras; e
- Velocidade média permitida.

### Peso 3 (Custo/Fatores de Risco)

- Condição geral.

## Exceções de nomenclatura das rodovias

Exceções nas nomenclaturas de via ocorreram pois em determinados casos há duas intersecções diferentes com as mesmas vias. A título de exemplo, a BR-242 abaixo, que tem duas intersecções com a TO-181 em dois vértices diferentes (não coincidentes).

Manter uma nomenclatura única para o caso acima causaria duplo entendimento até mesmo a nível de código, mesmo que com IDs separados. Portanto, fez-se necessária a nomenclatura no padrão *_n* onde *n* é um valor numérico da quantidade de intersecções das mesmas vias.

### BR-242/TO-181

![Exceção BR-242_TO-181.png](media/Exceo_BR-242_TO-181.png)

### BR-242/BR-010

![Exceção BR-242_BR-010.png](media/Exceo_BR-242_BR-010.png)

### TO-296/TO-050

![Exceção TO-296_TO-050.png](media/Exceo_TO-296_TO-050.png)

# Tarefas

## Criação da Base de Dados

- [x]  Localizar e, quando possível, obter fontes de dados, para utilização do projeto, como mapas e informações retirados de plataformas de geolocalização. As informações necessárias são:
    - [x]  Nome das cidades;
    - [x]  Nome das rodovias;
    - [x]  Distância entre cidades;
    - [x]  Presença de asfalto;
    - [x]  Presença de buracos;
    - [x]  Tipo de via (simples ou dupla);
    - [x]  Presença de pedágios;
    - [x]  Presença de obras ou manutenções;
    - [x]  Limite de velocidade; e
    - [x]  Estado geral da rodovia.
- [ ]  Transcrever todos os dados necessários para uma tabela no Google Planilhas.
    - [x]  Tabela de Adjacência;
    - [x]  Tabela de Incidência;
    
    ### Rodovias (Arestas)
    
    - [x]  Nome e Id das vias;
    - [x]  Nomes e Ids dos vértices (1 e 2);
    - [ ]  Condição Geral;
    - [ ]  Distância;
    - [ ]  Pavimentação;
    - [ ]  Buracos;
    - [ ]  Pedágios;
    - [ ]  Postos da PRF;
    - [ ]  Em obras; e
    - [ ]  Vel. Média permitida.
    
    ### Cidades (Vértices)
    
    - [x]  Id;
    - [x]  Nome dos vértices.
- [ ]  Transformar a tabela do Planilhas em um arquivo JSON utilizando o notebook específico do Google Colab.

## Camada de Dados

## Camada de Serviço

## CLI

# Extras

- [ ]  Incluir as tarifas de pedágios para o índice de degradação (custo) para uso da via.
- [ ]  Criar formas de inserir, atualizar ou deletar cidades e rodovias.

# Referências e Fontes de Dados

- Mapa Multimodal do Tocantins - DNIT (Disponível em: [https://www.gov.br/dnit/pt-br/assuntos/planejamento-e-pesquisa/dnit-geo/mapas-multimodais/mapas-2025/to.pdf/](https://www.gov.br/dnit/pt-br/assuntos/planejamento-e-pesquisa/dnit-geo/mapas-multimodais/mapas-2025/to.pdf/))
- Lei Complementar Nº 172, de 11 de Fevereiro de 2026 (Disponível em: [lei_172-2026_79220.PDF](https://www.al.to.leg.br/arquivos/lei_172-2026_79220.PDF))
- Wase (Disponível em: [https://www.waze.com/](https://www.waze.com/))
- Pesquisa CNT de Rodovias (Disponível em: [Pesquisa CNT de Rodovias](https://pesquisarodovias.cnt.org.br/mapa))
- Mapa de Manutenção - DNIT (Disponível em: [https://www.gov.br/dnit/pt-br/rodovias/mapa-de-gerenciamento/mapas-de-manutencao-fevereiro-2026](https://www.gov.br/dnit/pt-br/rodovias/mapa-de-gerenciamento/mapas-de-manutencao-fevereiro-2026))
- Portal de Notícias AGETO (Disponível em: [Notícias - Agência de Transportes, Obras e Infraestrutura - AGETO-TO](https://www.to.gov.br/ageto/noticias/data/2026/))
- Ecovias Araguaia - Condições da Via (Disponível em: [https://www.ecoviasaraguaia.com.br/condicoes-da-via](https://www.ecoviasaraguaia.com.br/condicoes-da-via))
- OpenStreetMap (Disponível em: [https://www.openstreetmap.org/](https://www.openstreetmap.org/))