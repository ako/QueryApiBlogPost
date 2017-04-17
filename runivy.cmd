rm userlib/*
java -jar build\apache-ivy-2.4.0\ivy-2.4.0.jar -settings ivysettings.xml -ivy ivy.xml -retrieve "userlib/[artifact]-[revision].[ext]"
