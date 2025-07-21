package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachment;

@UtilityClass
public class PermissionUtil {
	private AdvancedAPI plugin;

    public PermissionAttachment addPermission(Permissible permissible, String permission) {
		return permissible.addAttachment(plugin, permission, true);
	}
	
	public PermissionAttachment setPermission(Permissible permissible, String permission, boolean value) {
		return permissible.addAttachment(plugin, permission, value);
	}

	public void removePermission(Permissible permissible, PermissionAttachment attachment) {
		permissible.removeAttachment(attachment);
	}
}
