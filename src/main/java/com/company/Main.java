package com.company;

import api.API;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

public class Main {
    static API api = new API();

    public static void main(String[] args) {

        //Get input from user
        int characterId = getCharacterId();
        JSONObject character = api.getCharacter(characterId);

        printCharacter(character);

        //Get the house which the character belongs to
        List<String> houseURL = createJsonStringList(character, "allegiances");
        JSONObject house = api.get(houseURL.get(0));
        //Gets the members
        List<String> swornMembers = api.getSwornMembers(house);
        //Prints the sworn members if user want to see the sworn members then it will be printed out,
        // after that the BANTAMS BOOKS With Characters will be printed out
        printMembers(swornMembers);

    }

    private static int inputValidation() {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            try {
               return input = scanner.nextInt();

            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input. Please reenter: ");
                scanner.nextLine();
            }
        }

    }



    private static List<String> createJsonStringList(JSONObject json, String key) {
        JSONArray jsonArray = json.getJSONArray(key);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));

        }
        return list;
    }

    public static void printBantamsBooksAndItsPovCharacterS() {

        List<JSONObject> povBooks = api.getBantamBooks();
        for (JSONObject book: povBooks) {
            System.out.println("******************BANTAM BOOK*******************");
            System.out.println("\t\t\t\t"+book.getString("name"));
            //System.out.println(", Publisher: " + book.getString("publisher"));
            System.out.println("************************************************");
            System.out.println("\t\t\t\t*Pov Characters*\t\t\t\t");

            for (int i = 0; i <book.getJSONArray("povCharacters").length() ; i++) {
                String povUrl = book.getJSONArray("povCharacters").get(i).toString();
                JSONObject jsonObject = api.getPovCharacter(povUrl);
                System.out.println(jsonObject.getString("name"));
            }
        }



    }

    public static void printMembers(List<String> member) {
        System.out.println("Do you want to see the sworn members of the character you searched for? ");
        System.out.println("1. Yes");
        System.out.println("2. No");
        if(inputValidation()==2){System.exit(0); }

        //TRY with streams instead!
        System.out.println("******************Sworn members*******************");
        System.out.println();
        Map<String, Integer> members = new HashMap<>();
        for (int i = 0; i < member.size(); i++) {
            JSONObject jsonObject = api.get(member.get(i));
            members.put(jsonObject.getString("name"), i);
            //System.out.println(jsonObject.getString("name"));
        }
        //Takes a while to print out
        Collection<String> vals = members.keySet();
        vals.forEach(System.out::println);

        printBantamsBooksAndItsPovCharacterS();

    }


    public static void printCharacter(JSONObject jsonObject) {
        System.out.println("*********************");
        System.out.println("Name: " + jsonObject.getString("name"));
        System.out.println("Gender: " + jsonObject.getString("gender"));
        System.out.println("Culture: " + jsonObject.getString("culture"));
        System.out.println("Born: " + jsonObject.getString("born"));
        System.out.println("*********************");

    }

    public static int getCharacterId() {
        System.out.println("Enter character id: ");
        int characterId = inputValidation();
        return characterId;
    }



}
