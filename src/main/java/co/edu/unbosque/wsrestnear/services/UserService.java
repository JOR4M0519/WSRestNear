package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.*;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class UserService {

    private Connection conn;

    public UserService(){

    }

    public UserService(Connection conn) {
        this.conn = conn;
    }

    public List<User> listUsers() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<User> users = new ArrayList<User>();

        try {
            // Executing a SQL query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                String username = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String role = rs.getString("role");
                int fcoins = rs.getInt("fcoins");


                // Creating a new UserApp class instance and adding it to the array list
                users.add(new User(username,name, lastname, role, password, fcoins));
            }

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return users;
    }

    public User getUser(String username) {

        PreparedStatement stmt = null;
        User user = null;

        try {

            stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();


            rs.next();

            user = new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("role"),
                    rs.getString("password"),
                    rs.getInt("fcoins")
            );

            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }

    public User newUser(User user) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        if (user != null) {

            try {

                if (user.getRole().equals("Artista")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, fcoins)\n" +
                            "VALUES (?,?,?,?,'Artista',0)");
                }

                else if (user.getRole().equals("Comprador")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, fcoins)\n" +
                            "VALUES (?,?,?,?,'Comprador',0)");
                }
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getLastname());
                stmt.setString(4, user.getPassword());


                stmt.executeUpdate();
                stmt.close();
            } catch(SQLException se){
                se.printStackTrace(); // Handling errors from database
            } finally{
                // Cleaning-up environment
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return user;
        }
        else {
            return null;
        }


    }
    public User updateUser(User user, long parameter) {

        PreparedStatement stmt = null;
        User updatedUser = null;

        if (user != null) {

            try {

                stmt = this.conn.prepareStatement("UPDATE UserApp SET fcoins = ? WHERE user_id = ?");


                stmt.setLong(1, (user.getFcoins() + parameter));
                stmt.setString(2, user.getUsername());

                stmt.executeUpdate();

                stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
                stmt.setString(1, user.getUsername());
                ResultSet rs = stmt.executeQuery();


                rs.next();

                updatedUser = new User(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getInt("fcoins")
                );

                rs.close();
                stmt.close();

            } catch(SQLException se){
                se.printStackTrace();
            } finally{
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return updatedUser;
        }
        else {
            return null;
        }


    }
    //Retorna un array de las últimas colecciones agregadas al csv
    public List<Collection> getUltimasCollections() throws IOException {

        List<Collection> collectionList;
        List<Collection> respuesta = new ArrayList<>();


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
                Collections.reverse(collectionList);

                int numero=6;
                if(collectionList.size()<numero){
                    numero=collectionList.size();
                }
                for(int x=0;x<numero;x++){
                    respuesta.add(collectionList.get(x));
                }

            }
        }

        return respuesta;
    }

    //Retorna un array de todas las colecciones creadas por un artista en específico
    public List<Collection> getCollectionsPorArtista(String username) throws IOException {

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

                collectionList = csvToBean.parse().stream().filter(collection -> collection.getUsername().equals(username)).collect(Collectors.toList());
            }
        }

        return collectionList;
    }

    //Retorna un array de todas las colecciones creadas hasta el momento
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


    public Collection createCollection(String username,String collection,String quantity,String path) throws IOException {
        String newLine = username + "," + collection + "," + quantity+"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Collections.csv";
        System.out.println(fullpath);
        FileOutputStream os = new FileOutputStream(fullpath, true);
        os.write(newLine.getBytes());
        os.close();

        return new Collection(username,collection,quantity);
    }

    //Obtiene la cantidad de likes correspondientes a las imágenes NFT que hay en la plataforma
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

    //Obtiene un array correspondiente a la lista de usuarios registrados en la plataforma
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

    //Obtiene un array correspondiente a la lista de FCoins de los usuarios registrados en la plataforma
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

    //Obtiene un array correspondiente a la lista de NFTs que se encuentran creados en la plataforma
    /*public List<Art_NFT> getNft() throws IOException {

        List<Art_NFT> nft;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Nfts.csv")) {

           *//* if (is == null) {
                return Optional.empty();
            }*//*

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
        return nft;
    }*/

    //Se encarga de crear un nuevo usuario y de registrarlo en el csv para persistirlo
    public User createUser(String username, String name, String lastname, String password, String role, String Fcoins, String path) throws IOException {
            String newLine =  username + "," + name + ","+lastname+ "," + role + ","+ password +","+"0"+"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Users.csv";
        FileOutputStream os = new FileOutputStream(fullpath, true);
            os.write(newLine.getBytes());
            os.close();

        return new User(username, name, lastname, role, password, 0);
    }

    /*//Se encarga de agregar una cantidad de likes a una imagen NFT y de guardarla en el csv
    public Likes addLike(String email,String authorPictureEmail,String pictureName,int liker, String path) throws IOException {
        String newLine =  email + "," + authorPictureEmail + ","+pictureName+ "," + liker +"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Likes.csv";
        FileOutputStream os = new FileOutputStream(fullpath, true);
        os.write(newLine.getBytes());
        os.close();

        return new Likes(email,authorPictureEmail,pictureName);
    }

    public void updateLike(List<Likes> likesList,String path) throws IOException {

        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Likes.csv";
        PrintWriter writer = new PrintWriter(new File(fullpath));
        writer.print("");
        writer.close();

        FileOutputStream os = new FileOutputStream(fullpath, true);

        String newLine = "email,authorPictureEmail,pictureName,liker" +"\n";
        os.write(newLine.getBytes());
        for(Likes likes: likesList){
            newLine =  likes.getEmail() + "," + likes.getAuthorPictureEmail() + ","+likes.getPictureName()+ "," + likes.getLiker() +"\n";
            os.write(newLine.getBytes());
        }
        os.close();
    }*/

//    public FCoins createMoney(String username,String fcoins, String path) throws NullPointerException, IOException {
//        String newLine = username + "," + fcoins + "\n";
//        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "FCoins.csv";
//        System.out.println(fullpath);
//
//            FileOutputStream os = new FileOutputStream(fullpath, true);
//            os.write(newLine.getBytes());
//            os.close();
//            return new FCoins(username, fcoins);
//
//    }

    public void createNFT(String id, String collection, String title, String author, String price, String email_owner, String path) throws IOException {
        String newLine = id + "," + collection + "," + title + ","+author+ "," + price + ","+"0"+","+ email_owner +"\n";


        String fullpath = path +"WEB-INF"+File.separator+ "classes"+ File.separator+"Nfts.csv";
        FileOutputStream os = new FileOutputStream( fullpath, true);
        os.write(newLine.getBytes());
        os.close();

    }

    //Retorna un objeto de tipo FCoins, el cual tendrá al usuario del cual se solicitan los FCoins y la cantidad total contada de FCoins correspondientes a este usuario encontradas en el csv
//    public FCoins amountMoney(String username) throws IOException {
//
//        long amount = 0;
//
//
//        List<FCoins> fCoins = getFCoins().orElse(null);
//        if(fCoins!=null){
//            for(int i = 0; i < fCoins.size(); i++) {
//                if (fCoins.get(i).getUsername().equals(username)) {
//                    amount += Long.parseLong(fCoins.get(i).getFCoins());
//                }
//            }
//        }
//
//
//        return new FCoins(username,String.valueOf(amount));
//    }

    //Elimina un respectivo archivo del csv
    public static void deleteFile(String URL){
         new File(URL).delete();
    }

    //Genera un String de caracteres aleatorios
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

    }


}