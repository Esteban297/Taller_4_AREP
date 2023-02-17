# AREP Laboratorio 3

Creación de servidor web que tiene la capacidad de leer y mostrar archivos tipos jpg, js, html y css, que se encuentren en el disco local
Adicionalmente simula el motor spark para realizar peticiones de tipo get y post

## Iniciando

### Prerequisites

- Java - Ambiente de desarrollo
- Maven - Administrador de dependencias
- Git - Sistema de control de versiones

### Instalando el entorno

Descargamos el archivo .zip o lo clonamos con el comando:

```
git clone https://github.com/Esteban297/Taller_2_AREP.git
```

Una vez descargado el repositorio nos dirigimos al directorio raiz del proyecto y ejecutamos el comando:

```
mvn clean package exec:java -D "exec.mainClass"="edu.escuelaing.arep.app.webapp.FirstApp"
```

Finalmente ingrese al navegador de su preferencia y usamos el siguiente link :
http://localhost:35000


## Documentación

Para visualizar la documentación de javadoc ejecutamos el comando: 

```
mvn javadoc:javadoc
```



## Construido con

* [Maven](https://maven.apache.org/) - Dependency Management

## Versonamiento

Versión 1.0

## Autor

* Hayden Esteban Cristancho Pinzon
