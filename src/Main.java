import java.io.*;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Создано соединение между клиентом и сервером");

            while (!socket.isOutputShutdown()) {
                if (reader.ready()) {
                    String clientCommand = reader.readLine();
                    sendMessage(out, clientCommand);
                    if (clientCommand.equals("-1")) {
                        System.out.println("Получен запрос на уничтожение связи");
                        break;
                    }
                    String input_string = in.readLine();

                    if (!input_string.isEmpty()) {
                        System.out.println("Получены данные: ");
                        System.out.println(input_string);
                    }
                }
            }

            System.out.println("Связь уничтожена");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(BufferedWriter out, String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}