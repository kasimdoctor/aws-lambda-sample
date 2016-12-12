package io.spoonshift.utils.db;

import java.util.Iterator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class DynamoDbOperations {

	private DynamoDB dynamoDB;

	public DynamoDbOperations() {
		System.out.println("Connecting to DynamoDb...");
		dynamoDB = new DynamoDB(DynamoDbUtils.getDynamoDbClient(Regions.US_EAST_1));
	}

	public void readAndPrintExistingTableNames() {
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();

		Iterator<Table> iterator = tables.iterator();
		System.out.println("Tables in DynamoDb are: ");
		while (iterator.hasNext()) {
			Table table = iterator.next();
			System.out.println(table.getTableName());
		}
	}
	
	public void printTableData(String tableName, int pkValue) {
		Table table = dynamoDB.getTable(tableName);
		PrimaryKey primaryKey = new PrimaryKey();
		primaryKey.addComponent("id", pkValue);
		Item item = table.getItem(primaryKey);
		System.out.println(item.toString());

	}
	
	public void createItem(String tableName, int pkValue) {
		Table table = dynamoDB.getTable(tableName);
		
		Item item = new Item().withPrimaryKey("id", pkValue)
				.with("name", "Kasim Doctor")
				.with("isActive", false);
		
		table.putItem(item);
	}
}
