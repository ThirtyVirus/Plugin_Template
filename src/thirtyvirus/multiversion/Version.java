package thirtyvirus.multiversion;

import org.bukkit.Bukkit;

public enum Version {
	UNKNOWN, v1_7, v1_8, v1_9, v1_10, v1_11, v1_12, v1_13, v1_14, v1_14_3;

	public static Version getVersion() {
		if(Bukkit.getVersion().contains("1.7")) return v1_7;
		else if(Bukkit.getVersion().contains("1.8")) return v1_8;
		else if(Bukkit.getVersion().contains("1.9")) return v1_9;
		else if(Bukkit.getVersion().contains("1.10")) return v1_10;
		else if(Bukkit.getVersion().contains("1.11")) return v1_11;
		else if(Bukkit.getVersion().contains("1.12")) return v1_12;
		else if(Bukkit.getVersion().contains("1.13")) return v1_13;
		else if(Bukkit.getVersion().contains("1.14")) {

			if(Bukkit.getVersion().equals("1.14") || Bukkit.getVersion().contains("1.14 ")
					|| Bukkit.getVersion().contains("1.14.1") || Bukkit.getVersion().contains("1.14.2")
					|| Bukkit.getVersion().contains("1.14.3")) {
				return v1_14; } else { return v1_14_3; }

		}
		else return UNKNOWN;
	}

	public static String getBukkitVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	public boolean isBiggerThan(Version version) {
		int current = getIndex();
		int param = version.getIndex();

		return current > param;
	}

	public int getIndex() {
		int index = 0;

		for(Version v : values()) {
			if(this.equals(v)) return index;
			else index++;
		}

		return -1;
	}
}
