package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.*;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


public class UserService {

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

    //Crea una colección de un artista y después la agrega al csv para que persista
    public void createCollection(String username,String collection,String quantity) throws IOException {
        String newLine = username + "," + collection + "," + quantity+"\n";
        String is = UserService.class.getClassLoader().getResource("Collections.csv").getPath();
        String ruta= is.replace("/WSRestNear-1.0-SNAPSHOT/WEB-INF","");
        FileOutputStream os = new FileOutputStream(ruta, true);
        os.write(newLine.getBytes());
        os.close();
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
    public static Optional<List<Art_NFT>> getNft() throws IOException {

        List<Art_NFT> nft;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("Nfts.csv")) {

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

    //Se encarga de crear un nuevo usuario y de registrarlo en el csv para persistirlo
    public User createUser(String username, String name, String lastname, String password, String role, String Fcoins, String path) throws IOException {
            String newLine =  username + "," + name + ","+lastname+ "," + role + ","+ password +","+"0"+"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Users.csv";
        System.out.println(fullpath);
        FileOutputStream os = new FileOutputStream(fullpath, true);
            os.write(newLine.getBytes());
            os.close();

        return new User(username, name, lastname, role, password, "0");
    }

    //Se encarga de agregar una cantidad de likes a una imagen NFT y de guardarla en el csv
    public Likes addLike(String email,String authorPictureEmail,String pictureName,int liker, String path) throws IOException {
        String newLine =  email + "," + authorPictureEmail + ","+pictureName+ "," + liker +"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Likes.csv";
        FileOutputStream os = new FileOutputStream(fullpath, true);
        os.write(newLine.getBytes());
        os.close();

        return new Likes(email,authorPictureEmail,pictureName,liker);
    }

    //Se encarga de agregar una cantidad pasada por parámetros de FCoins a un respectivo usuario y después la guarda en el csv
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

    //Crea un NFT con las especificaciones solicitadas en los parámetros y después lo guarda en el csv
    public void createNFT(String id, String extension, String title, String author, String price, String email_owner, String path) throws IOException {
        String newLine = id + "," + extension + "," + title + ","+author+ "," + price + ","+"0"+","+ email_owner +"\n";

        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Nfts.csv";


        FileOutputStream os = new FileOutputStream( fullpath, true);
        os.write(newLine.getBytes());
        os.close();

    }

    //Retorna un objeto de tipo FCoins, el cual tendrá al usuario del cual se solicitan los FCoins y la cantidad total contada de FCoins correspondientes a este usuario encontradas en el csv
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