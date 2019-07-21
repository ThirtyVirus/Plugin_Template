package thirtyvirus.multiversion;

import org.bukkit.Bukkit;

public enum Version {
	UNKNOWN, v1_7, v1_8, v1_9, v1_10, v1_11, v1_12, v1_13, v1_14;
	
	public static Version getVersion() {
		if(Bukkit.getVersion().contains("1.7")) return v1_7;
		else if(Bukkit.getVersion().contains("1.8")) return v1_8;
		else if(Bukkit.getVersion().contains("1.9")) return v1_9;
		else if(Bukkit.getVersion().contains("1.10")) return v1_10;
		else if(Bukkit.getVersion().contains("1.11")) return v1_11;
		else if(Bukkit.getVersion().contains("1.12")) return v1_12;
		else if(Bukkit.getVersion().contains("1.13")) return v1_13;
		else if(Bukkit.getVersion().contains("1.14")) return v1_14;
		else return UNKNOWN;
	}
	
	public String getVersionName() {
		switch(this) {
			case v1_7:
				return "v1_7_R4";
			case v1_8:
				return "v1_8_R3";
			case v1_9:
				return "v1_9_R1";
			case v1_10:
				return "v1_10_R1";
			case v1_11:
				return "v1_11_R1";
			case v1_12:
				return "v1_12";
			case v1_13:
				return "v1_13";
			case v1_14:
				return "v1_14";
			default:
				return UNKNOWN.name();
		}
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
