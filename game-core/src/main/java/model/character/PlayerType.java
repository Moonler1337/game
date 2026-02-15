package model.character;

public enum PlayerType {
    WARRIOR, MAGE, ARCHER, HEALER, WITCH;

    public static PlayerType heroType(String a) {
        switch (a.toLowerCase()) {
            case "warrior":
                return PlayerType.WARRIOR;
            case "mage":
                return PlayerType.MAGE;
            case "archer":
                return PlayerType.ARCHER;
            case "healer":
                return PlayerType.HEALER;
            case "witch":
                return PlayerType.WITCH;
            default:
                throw new IllegalArgumentException(
                        "Invalid class name. Please enter a supported class.");
        }
    }

}
