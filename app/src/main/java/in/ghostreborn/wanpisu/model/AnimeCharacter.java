package in.ghostreborn.wanpisu.model;

public class AnimeCharacter {

    String role;
    String name;
    String image;

    public AnimeCharacter(
            String role,
            String name,
            String image
    ){
        this.role = role;
        this.name = name;
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
