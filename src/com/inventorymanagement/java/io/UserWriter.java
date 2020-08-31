/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/28/2020
 *  Time: 9:43 AM
 */
package com.inventorymanagement.java.io;


import com.google.gson.Gson;
import com.inventorymanagement.java.models.Auth;
import com.inventorymanagement.java.utils.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserWriter {
    private static Base64 base64 = new Base64();

    public UserWriter() {
        Path path = Paths.get("userData");
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void writeToFIle(Auth auth) {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(Constants.USERDATAFILE);) {
            gson.toJson(auth, writer);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Auth getCurrentUser() {
        Gson gson = new Gson();
        Auth auth = new Auth();
        try (Reader reader = new FileReader(Constants.USERDATAFILE)) {
            auth = gson.fromJson(reader, Auth.class);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String decodedUser = null;
        String decodedPassword = null;
        if (auth != null) {
            decodedUser = new String(base64.decode(auth.getEmail().getBytes()));
            decodedPassword = new String(base64.decode(auth.getPassword().getBytes()));
            return new Auth(decodedUser, DigestUtils.sha1Hex(decodedPassword));
        }
        return null;
    }


    public static void encodeUserToFile(String email, String password) {
        String encodedUser = new String(base64.encode(email.getBytes()));
        String encodedPassword = new String(base64.encode(password.getBytes()));
        writeToFIle(new Auth(encodedUser, DigestUtils.sha1Hex(encodedPassword)));
    }

    public static void clearCurrentUser() {
        try (Writer writer = new FileWriter(Constants.USERDATAFILE)) {
            writer.write("");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
