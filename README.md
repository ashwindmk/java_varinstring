
# Var-in-String

Simple utility Java class for resolving variables within String.


### Need

1. I wanted to create a reusable in-app template to greet users with their personalized variables.

2. These templates also need to be dynamic, i.e. should be able to update from server response (JSON).

3. With Var-in-String, we can send the template as string in JSON, for e.g. {"template": "Good morning, {user.name}!"}.


### Advantages

1. Use personalized values in generalized texts.

2. Allows fallback values.

3. Supports all data-types.


### Sample

```
Input: This is {user.name} ({user.age}) from {user.address.country}.
Output: This is Ashwin (25) from India.
```


### Usage

```java
        // Create data
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> user = new HashMap<>();
        user.put("name", "Ashwin");
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


        // Initialize VarinString with data
        VarinString varinString = new VarinString(map);


        // Simple test
        String input1 = "This is {user.name} ({user.age}) from {user.address.country}.";
        String output1 = varinString.resolve(input1);
        // output1: This is Ashwin (25) from India.


        // Test with fallback value
        String input2 = "We are from {user.address.state ?: 'a state'} in {user.address.country}.";
        String output2 = varinString.resolve(input2);
        // output2: We are from a state in India.
```
