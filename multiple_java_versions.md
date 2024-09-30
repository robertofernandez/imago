Puedes tener múltiples versiones de Java en la misma máquina y seleccionar cuál usar en cada proyecto. Para lograr esto, puedes configurar el entorno utilizando variables de entorno, definir el JDK en herramientas específicas como Maven o Gradle, o incluso utilizar un gestor de versiones como `jEnv` en sistemas Unix. Aquí te explico algunas opciones según el entorno que prefieras:

## 1. **Usar Múltiples Versiones de Java con Variables de Entorno**

### Configuración en Windows
1. **Descarga e instala** las versiones de JDK que deseas (por ejemplo, Java SE 8 y Java SE 17).
2. Configura la variable `JAVA_HOME` y `PATH` para cada versión. En Windows, esto se hace a través de:
   - Panel de control -> Sistema -> Configuración avanzada del sistema -> Variables de entorno.
3. Define una variable `JAVA_HOME` apuntando a la versión de Java que desees usar por defecto, y agrega la carpeta `bin` de esa versión al `PATH`.

Por ejemplo:

```plaintext
JAVA_HOME = C:\Program Files\Java\jdk1.8.0_271
PATH = %JAVA_HOME%\bin;%PATH%
```

Luego, cuando necesites cambiar a otra versión (por ejemplo, `jdk-17`):

```plaintext
JAVA_HOME = C:\Program Files\Java\jdk-17
PATH = %JAVA_HOME%\bin;%PATH%
```

**Tip**: Puedes crear scripts `.bat` para cambiar fácilmente de versión:

- `use-java8.bat`:
  ```plaintext
  set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_271
  set PATH=%JAVA_HOME%\bin;%PATH%
  ```
  
- `use-java17.bat`:
  ```plaintext
  set JAVA_HOME=C:\Program Files\Java\jdk-17
  set PATH=%JAVA_HOME%\bin;%PATH%
  ```

### Verificar la Versión de Java Usada
Después de cambiar las variables, verifica qué versión se está usando con:

```bash
java -version
```

## 3. **Configurar Múltiples Versiones de Java en Maven**

Para configurar un proyecto Maven específico con una versión distinta de Java, puedes hacerlo directamente en el archivo `pom.xml`:

1. Asegúrate de tener la versión correcta del JDK configurada en Maven.
2. Define la versión en el `pom.xml`:

```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

Incluso si tienes el `JAVA_HOME` globalmente configurado a una versión anterior (por ejemplo, Java 8), Maven usará la versión 17 para este proyecto específico.


## 5. **Alternativas para Usar Varias Versiones en la Misma Máquina**

Otra opción, si necesitas mantener varias versiones de Java para entornos específicos, es:

1. **Docker**: Puedes tener imágenes de Docker con diferentes versiones de Java y ejecutar tu aplicación dentro del contenedor con la versión que necesites.

2. **VirtualBox / WSL (Windows Subsystem for Linux)**: Tener entornos completamente separados para evitar interferencias.

## Recomendaciones Finales

1. En **Windows**, crea scripts `.bat` para cambiar entre versiones rápidamente.
2. Configura tu **IDE** para manejar diferentes versiones de Java a nivel de proyecto o módulo.
3. Usa herramientas específicas (como Maven o Gradle) para manejar la versión del compilador a nivel de proyecto.
