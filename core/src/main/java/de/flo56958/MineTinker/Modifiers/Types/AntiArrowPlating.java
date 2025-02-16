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

public class AntiArrowPlating extends Modifier {

	private static AntiArrowPlating instance;

	private AntiArrowPlating() {
		super(Main.getPlugin());
	}

	public static AntiArrowPlating instance() {
		synchronized (AntiArrowPlating.class) {
			if (instance == null) {
				instance = new AntiArrowPlating();
			}
		}

		return instance;
	}

	@Override
	public String getKey() {
		return "Anti-Arrow-Plating";
	}

	@Override
	public List<ToolType> getAllowedTools() {
		return Arrays.asList(ToolType.BOOTS, ToolType.LEGGINGS, ToolType.CHESTPLATE, ToolType.HELMET);
	}

	@Override
	public List<Enchantment> getAppliedEnchantments() {
		return Collections.singletonList(Enchantment.PROTECTION_PROJECTILE);
	}

	@Override
	public void reload() {
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);

		config.addDefault("Allowed", true);
		config.addDefault("Name", "Anti-Arrow-Plating");
		config.addDefault("ModifierItemName", "Pierce Resistant Metal");
		config.addDefault("Description", "Armor mitigates projectile damage!");
		config.addDefault("DescriptionModifierItem", "%WHITE%Modifier-Item for the Anti-Arrow-Plating-Modifier");
		config.addDefault("Color", "%WHITE%");
		config.addDefault("MaxLevel", 5);
		config.addDefault("OverrideLanguagesystem", false);

		config.addDefault("EnchantCost", 10);
		config.addDefault("Enchantable", false);

		config.addDefault("Recipe.Enabled", true);
		config.addDefault("Recipe.Top", "IAI");
		config.addDefault("Recipe.Middle", "ADA");
		config.addDefault("Recipe.Bottom", "IAI");

		Map<String, String> recipeMaterials = new HashMap<>();
		recipeMaterials.put("I", Material.IRON_BLOCK.name());
		recipeMaterials.put("A", Material.ARROW.name());
		recipeMaterials.put("D", Material.DIAMOND.name());

		config.addDefault("Recipe.Materials", recipeMaterials);

		// Save Config
		ConfigurationManager.saveConfig(config);
		ConfigurationManager.loadConfig("Modifiers" + File.separator, getFileName());

		// Initialize modifier
		init(Material.IRON_BLOCK, true);
	}

	@Override
	public boolean applyMod(Player p, ItemStack tool, boolean isCommand) {
		ItemMeta meta = tool.getItemMeta();

		if (meta != null) {
			if (ToolType.HELMET.contains(tool.getType()) || ToolType.CHESTPLATE.contains(tool.getType())
					|| ToolType.LEGGINGS.contains(tool.getType()) || ToolType.BOOTS.contains(tool.getType())) {

				meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, modManager.getModLevel(tool, this), true);
			}

			tool.setItemMeta(meta);
		}

		return true;
	}
}
