package io.spoonshift.domain.dao;

import java.util.Objects;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import io.spoonshift.domain.model.User;
import io.spoonshift.utils.json.Jackson;

public class UserDAO {
	private static final String TABLE_NAME = "users";

	private Table table;

	public UserDAO(DynamoDB dynamoDB) {
		this.table = dynamoDB.getTable(TABLE_NAME);
	}

	public User readById(int userId) {
		if(userId < 1) {
			throw new IllegalArgumentException("User Id not valid");
		}

		Item item = table.getItem(new PrimaryKey().addComponent("id", userId));
		return Jackson.fromJsonString(item.toJSON(), User.class);
	}
	
	public void addUser(User user) {
		Objects.requireNonNull(user);

		Item item = Item.fromJSON(Jackson.toJsonString(user));
		table.putItem(item);
	}
}
