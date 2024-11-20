package kz.hxncus.mc.advancedapi.bukkit.permission;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import org.bukkit.permissions.Permissible;

public class PermissionService {
	private static AdvancedAPI plugin;
	
	public PermissionService(AdvancedAPI plugin) {
	    PermissionService.plugin = plugin;
		register();
	}
	
	private void register() {
	
	}
	
	public void addPermission(Permissible permissible, String permission) {
		permissible.addAttachment(PermissionService.plugin, permission, true);
	}
	
	public void setPermission(Permissible permissible, String permission, boolean value) {
		permissible.addAttachment(PermissionService.plugin, permission, value);
	}
}
