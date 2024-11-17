package JDBC_Connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

public class DatafetchingFromYAMLandPropertiesFile {

	public String getValueFromProperties(Object key) throws IOException {
		String value = null;
		try {
			String path = System.getProperty("user.dir") + "\\credentials.properties";
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);

			Properties prop = new Properties();
			prop.load(stream);
			value = prop.get(key).toString();
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	/*
	 * Advantages of YAML File
	 * 
	 * Comments: using # are ignored and provide explanations. Key-Value Pairs: Data
	 * is organized using key-value pairs separated by colons (:). Nested
	 * Structures: Indentation defines nesting. Keys at the same level have the same
	 * indentation. Lists: Square brackets [] enclose ordered collections of items.
	 */

	private void dataFetchedFromYAMLFile() throws FileNotFoundException {

		String yamlFilePath = System.getProperty("user.dir") + "\\NewUpdatedYaml.yaml";
		try {
			File file = new File(yamlFilePath);
			FileInputStream stream = new FileInputStream(file);

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(stream);

			/*
			 * get data directly
			 */
			String url = (String) data.get("url");
			System.out.println("URL " + url);

			/*
			 * get data from simple heading
			 */

			Map<String, Object> db = (Map<String, Object>) data.get("database");
			String password = db.get("password").toString();
			System.out.println("Password " + password);

			/*
			 * get data from []
			 */

// Get the user_roles map
			Map<String, Object> userRoles = (Map<String, Object>) data.get("user_roles");

// Get the editor role data (assuming "editor" exists)
			Map<String, Object> editorRole = (Map<String, Object>) userRoles.get("editor");

// Get the permissions list (assuming "permissions" exists)
			List<String> permissions = (List<String>) editorRole.get("permissions");

			System.out.println("Editor permissions:");
			for (String permission : permissions) {
				System.out.println("- " + permission);
			}
		}

		catch (FileNotFoundException e) {
			System.err.println("File not found in : " + yamlFilePath);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error processing YAML file in : " + yamlFilePath);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		DatafetchingFromYAMLandPropertiesFile d = new DatafetchingFromYAMLandPropertiesFile();
		d.dataFetchedFromYAMLFile();
		d.getValueFromProperties("username");
	}
}
