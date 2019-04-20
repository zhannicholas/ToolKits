import java.nio.CharBuffer;
import java.io.IOException;
import java.util.*;

class PasswordGenerator{
    private static String[] characters = new String[]{
        "0123456789",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
        "~!@#$%^&*()_+-={}[];:,.<>?"
    };  

    private static String generatePassword(int length){
        StringBuilder allChars = new StringBuilder();
        CharBuffer buffer = CharBuffer.allocate(length);

        for(String chars: characters){
            fillRandomCharacters(chars, 1, buffer);
            allChars.append(chars);
        }

        fillRandomCharacters(allChars, length - buffer.position(), buffer);
        buffer.flip();
        randomize(buffer);

        return buffer.toString();
    }

    private static void fillRandomCharacters(CharSequence source, int count, Appendable target) {
        Random random = new Random();
        for(int i = 0; i < count; ++i) {
            try {
                target.append(source.charAt(random.nextInt(source.length())));
            } catch (IOException e) {
                throw new RuntimeException("Error appending chars");
            }
        }

    }

    private static void randomize(CharBuffer buffer) {
        Random random = new Random();
        for(int i = buffer.position(); i < buffer.limit(); ++i) {
            int n = random.nextInt(buffer.length());
            char c = buffer.get(n);
            buffer.put(n, buffer.get(i));
            buffer.put(i, c);
        }

    }

    public static void main(String[] args) throws Exception{
        int length = 10;
        if(args.length > 0){
            length = Integer.valueOf(args[0]);
            if(length < 0){
                System.out.println("密码长度必须大于0");
                return;
            }
        }
        System.out.println(generatePassword(length));
    }
}