package com.api.util;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDBUtility {
	private static VaultConfig vaultConfig;
	private static Vault vault;
	
	static {
		try {
			vaultConfig = new VaultConfig().address("http://13.62.49.234:8200")
					.token("root")
					.build();
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vault = new Vault(vaultConfig);
	}
	
	private VaultDBUtility() {
		
	}
	
	public static String getValueFromVault(String key) {
		LogicalResponse response = null;
		try {
			response = vault.logical().read("secret/pheonix/qa/database");
			
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,String> dataMap = response.getData();
		return dataMap.get(key);
	}

}
