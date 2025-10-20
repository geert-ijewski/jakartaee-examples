# A Hello World example

This example demonstrates a simple REST HelloWorld example.


# checkmk setup
```sh
docker exec -it checkmk su - monitoring -c '
cmk -I openliberty
cmk -I payara
cmk -O
'
```

# jolokia setup
```sh
mkdir -p jolokia
cd jolokia
curl -L -o jolokia-jvm.jar \
  https://repo1.maven.org/maven2/org/jolokia/jolokia-jvm/1.7.2/jolokia-jvm-1.7.2.jar
```