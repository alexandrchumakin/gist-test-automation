package com.github.alex_chumakin_test.data;

public class ContentController {

    private FileType fileType;

    public ContentController(FileType fileType) {
        this.fileType = fileType;
    }

    public String getContent() {
        switch (fileType) {
            case PYTHON:
                return "class HelloWorld:\\n\\n    def __init__(self, name):\\n        self.name = name.capitalize()" +
                        "\\n       \\n    def sayHi(self):\\n        print \\\"Hello \\\" + self.name + " +
                        "\\\"!\\\"\\n\\nhello = HelloWorld(\\\"world\\\")\\nhello.sayHi()";
            case XML:
                return "<object>\n" +
                        "<heading>Hello World</heading>\n" +
                        "<body>Simple hello world body</body>\n" +
                        "</object>";
            case PLAIN_TEXT:
            default:
                return "Hello world!";
        }
    }

    public String getFileName() {
        String extension;
        switch (fileType) {
            case PYTHON:
                extension = "py";
                break;
            case XML:
                extension = "xml";
                break;
            case PLAIN_TEXT:
            default:
                extension = "txt";
                break;
        }
        return String.format("hello_world.%s", extension);
    }

}
