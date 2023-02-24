# AREP Laboratorio 4

TALLER DE ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN

## Iniciando

### Prerequisites

- Java - Ambiente de desarrollo
- Maven - Administrador de dependencias
- Git - Sistema de control de versiones

### Instalando el entorno

Descargamos el archivo .zip o lo clonamos con el comando:

```
git clone https://github.com/Esteban297/Taller_4_AREP.git
```

Una vez descargado el repositorio nos dirigimos al directorio raiz del proyecto y ejecutamos el comando:

```
mvn clean package exec:java -D "exec.mainClass"="edu.escuelaing.arep.app.webapp.FirstApp"
```

Finalmente ingrese al navegador de su preferencia y usamos el siguiente link :
http://localhost:35000

#Para mostrar los diferentes archivos
Utilizamos las siguientes rutas
* Archivo html
```
http://localhost:35000/index
```

* Archivo css
```
http://localhost:35000/style
```

* Archivo jpg

```
http://localhost:35000/imagen
```

* Archivo js

```
http://localhost:35000/app
```


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
