import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarinStringDemo {
    public static void main(String[] args) {
        // Data
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> user = new HashMap<>();
        user.put("name", "John");
        user.put("age", 25);

        Map<String, Object> address = new HashMap<>();
        address.put("city", "Mumbai");
        address.put("country", "India");

        user.put("address", address);

        List<String> tags = new ArrayList<>();
        tags.add("MyBrand");
        tags.add("Titan");
        user.put("tags", tags);

        map.put("user", user);

        System.out.println("Data: " + map);

        // Initialize
        VarinString varinString = new VarinString(map);
        //varinString.enableLog(true);

        // Test 1
        String input1 = "This is {user.name} ({user.age}) from {user.address.country}.";
        String output1 = varinString.resolve(input1);
        System.out.println("\nInput 1: " + input1);
        System.out.println("Output 1: " + output1);

        // Test 2
        String input2 = "This is a simple plain text.";
        String output2 = varinString.resolve(input2);
        System.out.println("\nInput 2: " + input2);
        System.out.println("Output 2: " + output2);

        // Test 3
        String input3 = "We are from {user.address.state ?: 'a state'} in {user.address.country}.";
        String output3 = varinString.resolve(input3);
        System.out.println("\nInput 3: " + input3);
        System.out.println("Output 3: " + output3);

        // Test 4
        String input4 = "My name is {user.name}.";
        String output4 = varinString.resolve(input4);
        System.out.println("\nInput 4: " + input4);
        System.out.println("Output 4: " + output4);

        // Test 5
        String input5 = "Thank you for purchasing from {user.tags.1}.";
        String output5 = varinString.resolve(input5);
        System.out.println("\nInput 5: " + input5);
        System.out.println("Output 5: " + output5);

        // Test 6
        String input6 = "Thank you for replying our coordinator {user.firstname ?: ''}.";
        String output6 = varinString.resolve(input6);
        System.out.println("\nInput 6: " + input6);
        System.out.println("Output 6: " + output6);

        // Test 7
        String input7 = "Thank you for replying our coordinator {user.firstname ?: ''} at the earliest.";
        String output7 = varinString.resolve(input7);
        System.out.println("\nInput 7: " + input7);
        System.out.println("Output 7: " + output7);

        // Test 8
        String input8 = "The continent is {user.address.continent}.";
        String output8 = varinString.resolve(input8);
        System.out.println("\nInput 8: " + input8);
        System.out.println("Output 8: " + output8);

        // Test 9
        String input9 = "This is {user.address.continent ?:}";
        String output9 = varinString.resolve(input9);
        System.out.println("\nInput 9: " + input9);
        System.out.println("Output 9: " + output9);

        // Test 10
        String input10 = "This is an {?: 'important'} message.";
        String output10 = varinString.resolve(input10);
        System.out.println("\nInput 10: " + input10);
        System.out.println("Output 10: " + output10);
    }
}
