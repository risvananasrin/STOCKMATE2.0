import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String DB_URL = "jdbc:sqlite:stockmate.db";

    // ================= Create Table if Not Exists =================
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS items (" +
                    "item_id TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "category TEXT," +
                    "subcategory TEXT," +
                    "quantity INTEGER," +
                    "box_number TEXT" +
                    ");";

            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= Add Item =================
    public static boolean addItem(String itemId, String name, String category, String subcategory, int quantity, String box) {
        String sql = "INSERT INTO items(item_id,name,category,subcategory,quantity,box_number) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, itemId);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setString(4, subcategory);
            pstmt.setInt(5, quantity);
            pstmt.setString(6, box);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false; // e.g., ID already exists
        }
    }

    // ================= Update Item =================
    public static boolean updateItem(String itemId, String name, String category, String subcategory, int quantity, String box) {
        String sql = "UPDATE items SET name=?, category=?, subcategory=?, quantity=?, box_number=? WHERE item_id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setString(3, subcategory);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, box);
            pstmt.setString(6, itemId);

            int updated = pstmt.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= Get Item by ID =================
    public static String[] getItemById(String itemId) {
        String sql = "SELECT * FROM items WHERE item_id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return new String[]{
                        rs.getString("item_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("subcategory"),
                        String.valueOf(rs.getInt("quantity")),
                        rs.getString("box_number")
                };
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= Get All Items =================
    public static List<String[]> getAllItems() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                list.add(new String[]{
                        rs.getString("item_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("subcategory"),
                        String.valueOf(rs.getInt("quantity")),
                        rs.getString("box_number")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= Delete Item =================
    public static boolean deleteItem(String itemId) {
        String sql = "DELETE FROM items WHERE item_id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, itemId);
            int deleted = pstmt.executeUpdate();
            return deleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}