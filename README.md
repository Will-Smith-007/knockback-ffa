## Requirements
- You need a mysql/mariadb database.
- Knockback-FFA worlds.

## How to setup
- First set your database connection information in the config file and restart your server.
- Add a knockback world with `/knockback addWorld [World]`
- Set the world spawn for this world on your location with `/knockback setSpawn`
- Set the death height for this world on your location with `/knockback setDeath`

**Removing worlds**
- You can remove worlds from the map pool with `/knockback removeWorld [World]`

**Teleport through worlds**
- You can be teleported into all worlds with `/knockback tp [World]`

## Statistics
- You can see your statistics with `/stats`
- If a player joins the server, the stats will be loaded from the database async.
- The statistics will be then stored and updated in a local `PlayerStats` object to save performance.
- If a player left the server, the stats in the database will be updated based on the `PlayerStats` object.

## Permissions
- For the `/knockback` command you'll need the permission `knockback.setup`
- For the `/stats` command you don't need any permissions.
