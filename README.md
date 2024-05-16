# PWN3 Emulator Game Server

## Servidor Word, parcialmente implementado de forma simples, utilizando técnicas de engenharia reversa em pacotes de rede trafegados entre o cliente e o servidor oficial. Os dados foram analisados pela ferramenta [PWN3-Proxy](https://github.com/P15c1n4/PWN3-Proxy), com o intuito de criar uma prova de conceito para o TCC UNIP.

**USO**
<br>
Windows CMD:
<br />
`cd "pasta do servidor"`<br />
`java -jar Game_Server-RX.X.jar`
<br>

Nota: 
>É necessário estar na pasta do arquivo .jar para executa-lo!
<br/>

**ATENÇÃO**
<br />

>Este servidor é uma implementação simples e parcial, dependente do servidor [Master](https://github.com/P15c1n4/PWN3-Emulator-Master-Server), destinada apenas como prova de conceito. Não possui todas as funcionalidades e recursos do servidor original! E muito menos foram impregadas boas práticas de programação necessárias em um projeto real.
<br>

**Spawn** ✔
<br />
**Troca de arma/Reload** ✔
<br />
**Uso de Habilidades/Disparo** ✔
<br />
**Mobs Spawn/Move/Dead/** ✔
<br />
**Drop/Pick itens** ✔
<br />
**persistência de itens** ✔
<br />
**Comandos internos** ✔
<br />

### Servidor pronto
![Servidor iniciado aguardando conexão](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/4627d73f-d270-4987-bd81-d085298e64db)


### Reload
![Demostração Recarga de arma](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/3d0f1599-baf5-4bd5-8aa3-f5606d67dcad)


### Troca de arma/ Uso de Habilidades/ Recuperação de mana
![Demonstração uso de skill gasto de mana e troca de arma](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/a58ab0db-0517-4b4d-abd7-9b9ab8e4fae8)

### Mob movimentação/ Drop item/ Pick item
![Demonstração mob se movendo, drop e pick de itens](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/ff6b7f36-3e55-4eea-acb5-32e921d45e9b)

### Mob Respawn
![Demonstração de respawn de mobs](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/d6407ffe-46b5-4d02-abf8-8f9d34021c3a)

### Comandos do servidor 
>@loot 0-2<br />
>@makeitem item quantidade
<br />

![Demonstração comandos do servido](https://github.com/P15c1n4/PWN3-Emulator-Game-Server/assets/93447442/fe942667-8571-4604-a5d5-d6e6241582df)

