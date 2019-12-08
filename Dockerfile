FROM openjdk:11

ADD JavaCoreServer.jar jar_name.jar
ADD look_vm.html look_vm.html
ADD mainpage.html mainpage.html
ADD order_vm.html order_vm.html
ADD loginpage.html loginpage.html
CMD ["java","-jar","jar_name.jar"]
