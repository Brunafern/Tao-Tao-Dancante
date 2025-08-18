# Tão Tão Dançante - Jogo de ritmo


Este é um jogo rítmico inspirado no **Friday Night Funkin'**, desenvolvido em **Java**, usando a interface gráfica **JavaFX**. O projeto combina elementos musicais com uma narrativa inspirada no filme "Shrek".


## 🧾 Descrição


No controle de **Bardo**, um ogro corajoso e determinado, o jogador inicia em uma jornada para resgatar a princesa **Ruby**, sua esposa, que foi sequestrada por pretendentes ambiciosos. Esses rivais planejam forçá-la a um casamento com o intuito de assumir o trono do reino.
Para libertá-la, Bardo deverá enfrentar seus adversários em batalhas musicais, onde a vitória depende da habilidade de acompanhar o ritmo e pressionar as teclas corretas no tempo exato.


## 🧪 Tecnologias utilizadas


- **Java 21.0.7**
- **JavaFX 23.0.1** para a construção da interface gráfica
- **CSS** para a transparência de botões
- Assets personalizados desenvolvidos para o projeto com ajuda de inteligência artificial
- Música criada pelo Suno, inspirada na narrativa do jogo

## 🕹️ Como Executar

### Execute o arquivo abaixo

-   --add-modules javafx.controls,javafx.fxml \
  -jar target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar

### Caso não consiga rodar o arquivo, siga os passos abaixo

#### 1. Abrir o terminal


#### 2. Navegar até a pasta do projeto
- **cd /caminho/para/a/pasta/do/projeto**


#### 3. Compilar e empacotar o projeto (gera o arquivo JAR)
- **mvn clean package**


#### 4.Executar a aplicação
- Substitua /caminho/para/javafx-sdk/lib pelo caminho da pasta lib do seu JavaFX SDK e rode o comando:


- **java --module-path /caminho/para/javafx-sdk/lib \
  --add-modules javafx.controls,javafx.fxml \
  -jar target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar**


### Executar direto com Maven
Se preferir rodar a aplicação sem configurar manualmente o caminho do JavaFX, use o comando abaixo:
- **mvn javafx:run**


## Estrutura do sistema
![](https://i.imgur.com/Mu8b1Hj.png)

## 📸 Prints da Interface

### Tela de menu
![Tela menu](https://i.imgur.com/6AgksoJ.png)

### Tela de transição para a 1ª fase
![Tela de batalha](https://i.imgur.com/FqbuNCN.png)

### Tela da 1ª fase
![Tela de vitória](https://i.imgur.com/dD9UQul.png)

### Tela de pause
![Tela de vitória](https://i.imgur.com/eWRoLhg.png)

### Tela de vitória
![Tela menu](https://i.imgur.com/iQmsYHg.png)

### Tela de derrota
![Tela menu](https://i.imgur.com/hyyx7m0.png)



## ✨ Créditos e agradecimentos


Desenvolvimento: Aline Mouzinho, Bruna Fernanda e Camila Dornelas


Músicas: Criadas com Suno AI


Assets gráficos: Adaptados no Photoshop CS6 e criados com auxílio de IA


Inspiração: Filme Shrek e jogo Friday Night Funkin’
