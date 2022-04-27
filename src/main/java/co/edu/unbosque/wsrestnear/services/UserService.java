package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class UserService {

    public List<Collection> getCollections() throws IOException {

        List<Collection> collectionList;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Collections.csv")) {

            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br)
                        .withType(Collection.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                collectionList = csvToBean.parse();
            }
        }

        return collectionList;
    }

    public void createCollection(String username,String collection,String quantity, String path) throws IOException {
        String newLine = username + "," + collection + "," + quantity + "\n";

        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Collections.csv";

        FileOutputStream os = new FileOutputStream( fullpath, true);
        os.write(newLine.getBytes());
        os.close();

    }

    public List<Likes> getLikes() throws IOException {

        List<Likes> likesList;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Likes.csv")) {

            HeaderColumnNameMappingStrategy<Likes> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Likes.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Likes> csvToBean = new CsvToBeanBuilder<Likes>(br)
                        .withType(Likes.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                likesList = csvToBean.parse();
            }
        }

        return likesList;
    }

    public List<User> getUsers() throws IOException {

        List<User> users;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Users.csv")) {

            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(br)
                        .withType(User.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                users = csvToBean.parse();
            }
        }

        return users;
    }

    public Optional<List<FCoins>> getFCoins() throws IOException {

        List<FCoins> fcoins;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Fcoins.csv")) {

            if (is == null) {
                return Optional.empty();
            }
            HeaderColumnNameMappingStrategy<FCoins> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(FCoins.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                CsvToBean<FCoins> csvToBean = new CsvToBeanBuilder<FCoins>(br)
                        .withType(FCoins.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                fcoins = csvToBean.parse();
            }
        }
        return Optional.of(fcoins);
    }

    //Leer NFT
    public static Optional<List<Art_NFT>> getNft() throws IOException {

        List<Art_NFT> nft;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Nfts.csv")) {
            System.out.println("ruta cargue: "+ String.valueOf(UserService.class.getClassLoader()
                    .getResourceAsStream("Nfts.csv")));

            if (is == null) {
                return Optional.empty();
            }

            HeaderColumnNameMappingStrategy<Art_NFT> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Art_NFT.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Art_NFT> csvToBean = new CsvToBeanBuilder<Art_NFT>(br)
                        .withType(Art_NFT.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                nft = csvToBean.parse();
            }
        }
        return Optional.of(nft);
    }

    public User createUser(String username, String name, String lastname, String password, String role, String Fcoins, String path) throws IOException {
            String newLine =  username + "," + name + ","+lastname+ "," + role + ","+ password +","+"0"+"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Users.csv";
        System.out.println(fullpath);
        FileOutputStream os = new FileOutputStream(fullpath, true);
            os.write(newLine.getBytes());
            os.close();

        return new User(username, name, lastname, role, password, "0");
    }

    public Likes addLike(String email,String authorPictureEmail,String pictureName,int liker, String path) throws IOException {
        String newLine =  email + "," + authorPictureEmail + ","+pictureName+ "," + liker +"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Likes.csv";
        FileOutputStream os = new FileOutputStream(fullpath, true);
        os.write(newLine.getBytes());
        os.close();

        return new Likes(email,authorPictureEmail,pictureName,liker);
    }

    public FCoins createMoney(String username,String fcoins) throws NullPointerException, IOException {
        String newLine = username + "," + fcoins + "\n";
        String is = UserService.class.getClassLoader().getResource("Fcoins.csv").getPath();
        String ruta= is.replace("/WSRestNear-1.0-SNAPSHOT/WEB-INF","");

           System.out.println("Usuario: "+username+" FCoins: "+fcoins+" Path: "+ruta);
            if (is == null) {
                return null;
            }
            FileOutputStream os = new FileOutputStream(ruta, true);
            os.write(newLine.getBytes());
            os.close();
            return new FCoins(username, fcoins);

    }

    public void createNFT(String id, String collection, String title, String author, String price, String email_owner, String path) throws IOException {
        String newLine = id + "," + collection + "," + title + ","+author+ "," + price + ","+"0"+","+ email_owner +"\n";

        String fullpath = path +"WEB-INF"+File.separator+ "classes"+ File.separator+"Nfts.csv";
        FileOutputStream os = new FileOutputStream( fullpath, true);
        os.write(newLine.getBytes());
        os.close();

    }

   //<3 -----------------------------------------------------------------
    public FCoins amountMoney(String username) throws IOException {

        long amount = 0;
        boolean existe = false;

        List<FCoins> fCoins = getFCoins().orElse(null);
        if(fCoins!=null){
            for(int i = 0; i < fCoins.size(); i++) {
                if (fCoins.get(i).getUsername().equals(username)) {
                    amount += Long.parseLong(fCoins.get(i).getFCoins());
                    existe = true;
                }
            }
        }
        if(!existe){
            return null;
        }

        return new FCoins(username,String.valueOf(amount));
    }
    //<3 -----------------------------------------------------------------

    public static void deleteFile(String URL){
         new File(URL).delete();
    }

    public String generateRandomString() {
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

        return generatedString;
    }

    public static void main(String args[]) {

        /*try {
            Optional<List<User>> users = new UserService().getUsers();

            for (User user: users.get()) {
                System.out.println(user.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}