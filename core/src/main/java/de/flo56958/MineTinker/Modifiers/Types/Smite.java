package de.flo56958.MineTinker.Modifiers.Types;

import de.flo56958.MineTinker.Data.ToolType;
import de.flo56958.MineTinker.Main;
import de.flo56958.MineTinker.Modifiers.Modifier;
import de.flo56958.MineTinker.Utilities.ConfigurationManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class Smite extends Modifier {

	private static Smite instance;

	private Smite() {
		super(Main.getPlugin());
	}

	public static Smite instance() {
		synchronized (Smite.class) {
			if (instance == null) {
				instance = new Smite();
			}
		}

		return instance;
	}

	@Override
	public String getKey() {
		return "Smite";
	}

	@Override
	public List<ToolType> getAllowedTools() {
		return Arrays.asList(ToolType.SWORD, ToolType.AXE);
	}

	@Override
	public List<Enchantment> getAppliedEnchantments() {
		return Collections.singletonList(Enchantment.DAMAGE_UNDEAD);
	}

	@Override
	public void reload() {
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);

		config.addDefault("Allowed", true);
		config.addDefault("Name", "Smite");
		config.addDefault("ModifierItemName", "Holy Bone");
		config.addDefault("Description", "Weapon does additional damage towards the Undead!");
		config.addDefault("DescriptionModifierItem", "%YELLOW%Modifier-Item for the Smite-Modifier");
		config.addDefault("Color", "%YELLOW%");
		config.addDefault("MaxLevel", 5);
		config.addDefault("OverrideLanguagesystem", false);

		config.addDefault("EnchantCost", 10);
		config.addDefault("Enchantable", false);

		config.addDefault("Recipe.Enabled", true);
		config.addDefault("Recipe.Top", "BMB");
		config.addDefault("Recipe.Middle", "MIM");
		config.addDefault("Recipe.Bottom", "BMB");

		Map<String, String> recipeMaterials = new HashMap<>();
		recipeMaterials.put("B", Material.BONE.name());
		recipeMaterials.put("M", Material.BONE_MEAL.name());
		recipeMaterials.put("I", Material.IRON_INGOT.name());

		config.addDefault("Recipe.Materials", recipeMaterials);

		ConfigurationManager.saveConfig(config);
		ConfigurationManager.loadConfig("Modifiers" + File.separator, getFileName());

		init(Material.BONE, true);
	}

	@Override
	public boolean applyMod(Player p, ItemStack tool, boolean isCommand) {
		ItemMeta meta = tool.getItemMeta();

		if (meta != null) {
			if (ToolType.AXE.contains(tool.getType()) || ToolType.SWORD.contains(tool.getType())) {
				meta.addEnchant(Enchantment.DAMAGE_UNDEAD, modManager.getModLevel(tool, this), true);
			}

			tool.setItemMeta(meta);
		}

		return true;
	}
}
