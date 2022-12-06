
# Tech test

Java 17 is required*

Estructuras de datos utilizadas en el algoritmo
* Map: Para hacer un diccionario de los diferentes objetos por su id para poder buscarlos de forma eficiente
* Inventory: Objeto principal donde se almacena el directorio de productos
* Product: Objeto que detalla la informacion basica de cada producto e incluye el diccionario de sus diferentes tallas
* Size: Objeto que detalla la informacion basica de cada talla ademas de inlcuir su inventario


## Running Tests

To run tests, run the following command

```bash
  mvn test
```


## Run
Clone the project

```bash
  git clone https://github.com/jimontoyag/capitole-inditex.git
```

Go to the project directory

```bash
  cd capitole-inditex
```

Generate executable jar

```bash
  mvn package
```

Execute

```bash
  java -jar target/inditex-1.0-SNAPSHOT-jar-with-dependencies.jar pathTo/product.csv pathTo/size.csv pathTo/stock.csv
```
*You should pass 3 parameters with the abosulte path of the file in the order listed before

## Authors

- [@jimontoyag](https://www.github.com/jimontoyag)

