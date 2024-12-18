package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachment;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PermissionUtil {
    public PermissionAttachment addPermission(Permissible permissible, String permission) {
		return permissible.addAttachment(AdvancedAPI.getInstance(), permission, true);
	}
	
	public PermissionAttachment setPermission(Permissible permissible, String permission, boolean value) {
		return permissible.addAttachment(AdvancedAPI.getInstance(), permission, value);
	}

	public void removePermission(Permissible permissible, PermissionAttachment attachment) {
		permissible.removeAttachment(attachment);
	}
}
