# rabbitmq-intro
Este projeto foi feito para dar uma introdução ao RabbitMQ e exemplificar o seu uso solucionando um problema de comunicação com um serviço terceiro instável.

Para executá-lo, basta executar o seguinte comando na pasta raiz:
```
./mvnw.cmd -f ./consumer/pom.xml clean package; ./mvnw.cmd -f ./producer/pom.xml clean package; docker-compose up -d
```

Se você ficou interessado nesse projeto e quer saber mais, acesse o artigo:
https://medium.com/@lucasribeirobarzotto/rabbitmq-introdu%C3%A7%C3%A3o-ebe1645a2fa8 onde eu explico com mais detalhes o seu funcionamento.