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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piercing extends Modifier {

	private static Piercing instance;

	private Piercing() {
		super(Main.getPlugin());
	}

	public static Piercing instance() {
		synchronized (Piercing.class) {
			if (instance == null) {
				instance = new Piercing();
			}
		}

		return instance;
	}

	@Override
	public String getKey() {
		return "Piercing";
	}

	@Override
	public List<ToolType> getAllowedTools() {
		return Collections.singletonList(ToolType.CROSSBOW);
	}

	@Override
	public List<Enchantment> getAppliedEnchantments() {
		return Collections.singletonList(Enchantment.PIERCING);
	}

	@Override
	public void reload() {
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);

		config.addDefault("Allowed", true);
		config.addDefault("Name", "Piercing");
		config.addDefault("ModifierItemName", "Bodkin Point");
		config.addDefault("Description", "Passes through enemies!");
		config.addDefault("DescriptionModifierItem", "%GRAY%Modifier-Item for the Piercing-Modifier");
		config.addDefault("Color", "%GRAY%");
		config.addDefault("MaxLevel", 4);
		config.addDefault("OverrideLanguagesystem", false);

		config.addDefault("EnchantCost", 10);
		config.addDefault("Enchantable", false);

		config.addDefault("Recipe.Enabled", true);
		config.addDefault("Recipe.Top", "FIF");
		config.addDefault("Recipe.Middle", "OAO");
		config.addDefault("Recipe.Bottom", "FIF");

		Map<String, String> recipeMaterials = new HashMap<>();
		recipeMaterials.put("F", Material.FLINT.name());
		recipeMaterials.put("I", Material.IRON_INGOT.name());
		recipeMaterials.put("O", Material.OAK_PLANKS.name());
		recipeMaterials.put("A", Material.ARROW.name());

		config.addDefault("Recipe.Materials", recipeMaterials);

		ConfigurationManager.saveConfig(config);
		ConfigurationManager.loadConfig("Modifiers" + File.separator, getFileName());

		init(Material.ARROW, true);
	}

	@Override
	public boolean applyMod(Player p, ItemStack tool, boolean isCommand) {
		ItemMeta meta = tool.getItemMeta();

		if (meta != null) {
			if (ToolType.CROSSBOW.contains(tool.getType())) {
				meta.addEnchant(Enchantment.PIERCING, modManager.getModLevel(tool, this), true);
			}

			tool.setItemMeta(meta);
		}

		return true;
	}
}
