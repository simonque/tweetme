package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class Tweet extends Model implements Serializable {
	// Serialization ID.
	// TODO: Try Parcelable instead.
	private static final long serialVersionUID = -4561762185420913284L;

	@Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;			// Unique ID of tweet.
	@Column(name = "body")
	private String body;		// Content of tweet.
	@Column(name = "timestamp")
	private String timestamp;	// Time this tweet was created.
	@Column(name = "user")
	private User user;

	// The default constructor is required for ActiveAndroid's Model class.
	public Tweet() {
		super();
	}

	public static Tweet fromJSON(JSONObject json) {
		Tweet tweet = new Tweet();
		// Extract values from JSON object to fill in the fields.
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.timestamp = json.getString("created_at");
			tweet.user = User.fromJSON(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray array) {
		ArrayList<Tweet> tweet_array = new ArrayList<Tweet>(array.length());
		for (int i = 0; i < array.length(); ++i) {
			JSONObject tweet_object = null;
			try {
				tweet_object = array.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJSON(tweet_object);
			if (tweet != null) {
				tweet_array.add(tweet);
			}
		}
		return tweet_array;
	}

	@Override
	public String toString() {
		return getUser().getScreenName() + ": " + getBody();
	}

	public String getBody() {
		return body;
	}

	public long getUniqueId() {
		return uid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public User getUser() {
		return user;
	}
}
