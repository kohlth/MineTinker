package de.flo56958.MineTinker.Data;

import de.flo56958.MineTinker.Main;
import de.flo56958.MineTinker.Utilities.ConfigurationManager;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lists {

	public static final HashMap<Player, BlockFace> BLOCKFACE = new HashMap<>();
	private static final FileConfiguration config = Main.getPlugin().getConfig();
	public static final List<String> DROPLOOT = config.getStringList("LevelUpEvents.DropLoot.Items");
	public static List<String> WORLDS;
	public static List<String> WORLDS_BUILDERSWANDS;
	public static List<String> WORLDS_EASYHARVEST;

	static {
		reload();
	}

	public static void reload() {
		WORLDS = config.getStringList("BannedWorlds");
		WORLDS_BUILDERSWANDS = ConfigurationManager.getConfig("BuildersWand.yml").getStringList("BuildersWand.BannedWorlds");
		WORLDS_EASYHARVEST = config.getStringList("EasyHarvest.BannedWorlds");
	}

	public static ArrayList<Material> getLetherArmor() {
		ArrayList<Material> mats = new ArrayList<>();

		mats.add(Material.LEATHER_CHESTPLATE);
		mats.add(Material.LEATHER_HELMET);
		mats.add(Material.LEATHER_BOOTS);
		mats.add(Material.LEATHER_LEGGINGS);

		return mats;
	}

	/**
	 * @return All Leaf Type
	 */
	public static ArrayList<Material> getWoodLeaves() {
		ArrayList<Material> mats = new ArrayList<>();

		mats.add(Material.ACACIA_LEAVES);
		mats.add(Material.BIRCH_LEAVES);
		mats.add(Material.DARK_OAK_LEAVES);
		mats.add(Material.JUNGLE_LEAVES);
		mats.add(Material.OAK_LEAVES);
		mats.add(Material.SPRUCE_LEAVES);

		return mats;
	}

	/**
	 * @return All Plank types
	 */
	public static ArrayList<Material> getWoodPlanks() {
		ArrayList<Material> mats = new ArrayList<>();
		mats.add(Material.ACACIA_PLANKS);
		mats.add(Material.BIRCH_PLANKS);
		mats.add(Material.DARK_OAK_PLANKS);
		mats.add(Material.JUNGLE_PLANKS);
		mats.add(Material.OAK_PLANKS);
		mats.add(Material.SPRUCE_PLANKS);
		return mats;
	}

	/**
	 * @return All Log types
	 */
	public static ArrayList<Material> getWoodLogs() {
		ArrayList<Material> mats = new ArrayList<>();
		mats.add(Material.ACACIA_LOG);
		mats.add(Material.BIRCH_LOG);
		mats.add(Material.DARK_OAK_LOG);
		mats.add(Material.JUNGLE_LOG);
		mats.add(Material.OAK_LOG);
		mats.add(Material.SPRUCE_LOG);
		return mats;
	}

	/**
	 * @return All stripped Log types
	 */
	public static ArrayList<Material> getWoodStrippedLogs() {
		ArrayList<Material> mats = new ArrayList<>();
		mats.add(Material.STRIPPED_ACACIA_LOG);
		mats.add(Material.STRIPPED_BIRCH_LOG);
		mats.add(Material.STRIPPED_DARK_OAK_LOG);
		mats.add(Material.STRIPPED_JUNGLE_LOG);
		mats.add(Material.STRIPPED_OAK_LOG);
		mats.add(Material.STRIPPED_SPRUCE_LOG);
		return mats;
	}

	/**
	 * @return All Wood types
	 */
	public static ArrayList<Material> getWoodWood() {
		ArrayList<Material> mats = new ArrayList<>();
		mats.add(Material.ACACIA_WOOD);
		mats.add(Material.BIRCH_WOOD);
		mats.add(Material.DARK_OAK_WOOD);
		mats.add(Material.JUNGLE_WOOD);
		mats.add(Material.OAK_WOOD);
		mats.add(Material.SPRUCE_WOOD);
		return mats;
	}

	/**
	 * @return All stripped Wood types
	 */
	public static ArrayList<Material> getWoodStrippedWood() {
		ArrayList<Material> mats = new ArrayList<>();
		mats.add(Material.STRIPPED_ACACIA_WOOD);
		mats.add(Material.STRIPPED_BIRCH_WOOD);
		mats.add(Material.STRIPPED_DARK_OAK_WOOD);
		mats.add(Material.STRIPPED_JUNGLE_WOOD);
		mats.add(Material.STRIPPED_OAK_WOOD);
		mats.add(Material.STRIPPED_SPRUCE_WOOD);
		return mats;
	}

}
