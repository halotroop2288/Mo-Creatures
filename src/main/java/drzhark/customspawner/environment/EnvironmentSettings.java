package drzhark.customspawner.environment;

import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.biomes.BiomeData;
import drzhark.customspawner.biomes.BiomeGroupData;
import drzhark.customspawner.biomes.BiomeModData;
import drzhark.customspawner.configuration.CMSConfigCategory;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.configuration.CMSProperty;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.entity.EntityModData;
import drzhark.customspawner.registry.StructureRegistry;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSLog;
import drzhark.customspawner.utils.CMSUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EnvironmentSettings {

    public boolean worldGenCreatureSpawning = true;
    public boolean killallUseLightLevel;
    public boolean killallVillagers;
    public boolean enforceMaxSpawnLimits;
    public int minDespawnLightLevel = 2;
    public int maxDespawnLightLevel = 7;
    public boolean forceDespawns;
    public boolean debug;
    private File ROOT_PATH;
    private String name;
    private Class<? extends WorldProvider> worldProviderClass;

    public Map<String, EntityData> entityMap = new TreeMap<String, EntityData>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, EnumCreatureType> entityTypes = new HashMap<String, EnumCreatureType>();
    public Map<String, BiomeData> biomeMap = new TreeMap<String, BiomeData>();
    public Map<String, BiomeGroupData> biomeGroupMap = new TreeMap<String, BiomeGroupData>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, EntityModData> entityModMap = new TreeMap<String, EntityModData>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, EntityModData> defaultModMap = new TreeMap<String, EntityModData>(String.CASE_INSENSITIVE_ORDER);
    public Map<String, BiomeModData> biomeModMap = new TreeMap<String, BiomeModData>(String.CASE_INSENSITIVE_ORDER);
    public Map<Class<? extends Entity>, EntityData> classToEntityMapping = new HashMap<Class<? extends Entity>, EntityData>();
    public Map<String, EntitySpawnType> entitySpawnTypes = new TreeMap<String, EntitySpawnType>(String.CASE_INSENSITIVE_ORDER);
    public EntitySpawnType LIVINGTYPE_UNDEFINED = new EntitySpawnType(this, EntitySpawnType.UNDEFINED, 0, 0, 0.0F, false);
    public EntitySpawnType LIVINGTYPE_CREATURE = new EntitySpawnType(this, EntitySpawnType.CREATURE, 400, 40, 0.1F, true);
    public EntitySpawnType LIVINGTYPE_AMBIENT = new EntitySpawnType(this, EntitySpawnType.AMBIENT, 100, 20);
    public EntitySpawnType LIVINGTYPE_WATERCREATURE = new EntitySpawnType(this, EntitySpawnType.WATERCREATURE, 100, 25, Material.WATER);
    public EntitySpawnType LIVINGTYPE_MONSTER = new EntitySpawnType(this, EntitySpawnType.MONSTER, 1, 70);
    public EntitySpawnType LIVINGTYPE_UNDERGROUND = new EntitySpawnType(this, EntitySpawnType.UNDERGROUND, 400, 15, 0, 63, 0.0F, false);
    public Map<String, String> worldEnvironmentMap = new HashMap<String, String>();
    public StructureRegistry structureData = new StructureRegistry();

    private static final String CATEGORY_CUSTOMSPAWNER_SETTINGS = "customspawner-settings";
    private static final String CATEGORY_BIOMEGROUP_DEFAULTS = "biomegroup-defaults";
    private static final String CATEGORY_MOD_MAPPINGS = "mod-mappings";
    private static final String CREATURES_FILE_PATH = File.separator + "Creatures" + File.separator;
    private static final String BIOMES_FILE_PATH = File.separator + "Biomes" + File.separator;

    public CMSConfiguration CMSEnvironmentConfig;
    public CMSConfiguration CMSStructureConfig;
    public CMSConfiguration CMSEntityBiomeGroupsConfig;
    public CMSConfiguration CMSLivingSpawnTypeConfig;
    public CMSLog envLog;

    public EnvironmentSettings(File root, String name, Class<? extends WorldProvider> worldProviderClass) {
        this.ROOT_PATH = root;
        this.envLog = new CMSLog(name);
        this.name = name;
        this.worldProviderClass = worldProviderClass;
        registerConfigs();
        registerLivingSpawnTypes();
        genModConfiguration();
        readConfigValues();
    }

    public String name() {
        return this.name;
    }

    public Class<? extends WorldProvider> getWorldProviderClass() {
        return this.worldProviderClass;
    }

    private void registerConfigs() {
        this.CMSEnvironmentConfig = new CMSConfiguration(new File(this.ROOT_PATH, "Environment.cfg"));
        this.CMSStructureConfig = new CMSConfiguration(new File(this.ROOT_PATH, "Structures.cfg"));
        this.CMSLivingSpawnTypeConfig = new CMSConfiguration(new File(this.ROOT_PATH, "EntitySpawnTypes.cfg"));
        this.CMSEntityBiomeGroupsConfig = new CMSConfiguration(new File(this.ROOT_PATH, "EntityBiomeGroups.cfg"));
        this.CMSEnvironmentConfig.load();
        this.CMSEntityBiomeGroupsConfig.load();
        this.CMSStructureConfig.load();
        this.CMSLivingSpawnTypeConfig.load();
        this.envLog.logger.info("Initializing WorldSettings Config File at " + this.ROOT_PATH + "...");
    }

    public void registerLivingSpawnTypes() {
        // register defaults
        this.entitySpawnTypes.put(EntitySpawnType.UNDEFINED, this.LIVINGTYPE_UNDEFINED);
        this.entitySpawnTypes.put(EntitySpawnType.CREATURE, this.LIVINGTYPE_CREATURE);
        this.entitySpawnTypes.put(EntitySpawnType.AMBIENT, this.LIVINGTYPE_AMBIENT);
        this.entitySpawnTypes.put(EntitySpawnType.MONSTER, this.LIVINGTYPE_MONSTER);
        this.entitySpawnTypes.put(EntitySpawnType.WATERCREATURE, this.LIVINGTYPE_WATERCREATURE);
        this.entitySpawnTypes.put(EntitySpawnType.UNDERGROUND, this.LIVINGTYPE_UNDERGROUND);
        // make sure defaults are always created
        for (EntitySpawnType entitySpawnType : this.entitySpawnTypes.values()) {
            if (entitySpawnType.name().equalsIgnoreCase("UNDEFINED")) {
                continue;
            }
            if (!this.CMSLivingSpawnTypeConfig.hasCategory(entitySpawnType.name().toLowerCase())) {
                CMSConfigCategory configCat = this.CMSLivingSpawnTypeConfig.getCategory(entitySpawnType.name().toLowerCase());
                configCat.put("SpawnTickRate", new CMSProperty("spawntickrate", Integer.toString(entitySpawnType.getSpawnTickRate()),
                        CMSProperty.Type.INTEGER));
                configCat.put("MobSpawnRange", new CMSProperty("mobspawnrange", Integer.toString(entitySpawnType.getMobSpawnRange()),
                        CMSProperty.Type.INTEGER));
                configCat.put("minSpawnHeight", new CMSProperty("minspawnheight", Integer.toString(entitySpawnType.getMinSpawnHeight()),
                        CMSProperty.Type.INTEGER));
                configCat.put("maxSpawnHeight", new CMSProperty("maxspawnheight", Integer.toString(entitySpawnType.getMaxSpawnHeight()),
                        CMSProperty.Type.INTEGER));
                configCat.put("SpawnCap", new CMSProperty("spawncap", Integer.toString(entitySpawnType.getSpawnCap()), CMSProperty.Type.INTEGER));
                configCat.put("ChunkGenSpawnChance", new CMSProperty("chunkgenspawnchance", Float.toString(entitySpawnType.getChunkSpawnChance()),
                        CMSProperty.Type.DOUBLE));
                configCat.put(
                        "ShouldSeeSky",
                        new CMSProperty("shouldseesky", entitySpawnType.getShouldSeeSky() == null ? "UNDEFINED" : Boolean.toString(entitySpawnType
                                .getShouldSeeSky()), CMSProperty.Type.STRING));
            }
        }
        // check config for new values
        for (CMSConfigCategory spawnCat : this.CMSLivingSpawnTypeConfig.categories.values()) {
            try {
                for (Map.Entry<String, CMSProperty> propEntry : spawnCat.getValues().entrySet()) {
                    EntitySpawnType entitySpawnType = this.entitySpawnTypes.get(spawnCat.getQualifiedName());
                    if (entitySpawnType == null) {
                        entitySpawnType =
                                this.entitySpawnTypes.put(spawnCat.getQualifiedName(), new EntitySpawnType(this, spawnCat.getQualifiedName()));
                    }
                    if (propEntry.getKey().equalsIgnoreCase("chunkgenspawnchance")) {
                        entitySpawnType.setChunkSpawnChance(Float.parseFloat(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("mobspawnrange")) {
                        entitySpawnType.setMobSpawnRange(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("minspawnheight")) {
                        entitySpawnType.setMinSpawnHeight(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("maxspawnheight")) {
                        entitySpawnType.setMaxSpawnHeight(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("spawncap")) {
                        entitySpawnType.setSpawnCap(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("spawntickrate")) {
                        entitySpawnType.setSpawnTickRate(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("spawncap")) {
                        entitySpawnType.setSpawnCap(Integer.parseInt(propEntry.getValue().value));
                    } else if (propEntry.getKey().equalsIgnoreCase("shouldseesky") && !propEntry.getValue().value.equalsIgnoreCase("UNDEFINED")) {
                        entitySpawnType.setShouldSeeSky(Boolean.parseBoolean(propEntry.getValue().value));
                    }
                }
            } catch (Throwable e) {

            }
        }
        this.CMSLivingSpawnTypeConfig.save();
        // register custom handlers
    }

    public void updateSettings() {
        readConfigValues();
        // make sure latest config data is loaded, fixes issue where mocreatures don't spawn on initial load
        this.CMSEnvironmentConfig.load();
        this.CMSStructureConfig.load();
        populateSpawnBiomes();
        populateSpawns();
    }

    public void initializeEntities() {
        for (Map.Entry<Class<? extends Entity>, String> entry : EntityList.classToStringMapping.entrySet()) {
            Class<? extends Entity> clazz = entry.getKey();
            if (this.classToEntityMapping.get(clazz) == null) { // don't process if it already exists
                registerEntity(clazz);
            } else {
                processEntityConfig(this.classToEntityMapping.get(clazz));
            }
        }
        this.CMSEnvironmentConfig.save();
    }

    @SuppressWarnings("unchecked")
    public EntityData registerEntity(Class<? extends Entity> clazz) {
        EntityLiving entityliving = null;
        EntityData entityData = null;
        try {
            if (this.debug) {
                this.envLog.logger.info("Attempting to register Entity from class " + clazz + "...");
            }
            entityliving = (EntityLiving) clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {DimensionManager.getWorld(0)});
        } catch (Throwable throwable) {
            if (this.debug) {
                this.envLog.logger.info(clazz + " is not a valid Entity for registration, Skipping...");
            }
            return null;
        }

        String entityName = (String) EntityList.classToStringMapping.get(clazz);

        if (this.debug) {
            this.envLog.logger.info("Starting registration for " + entityName);
        }
        if (entityName.contains(".")) {
            if ((entityName.indexOf(".") + 1) < entityName.length()) {
                entityName = entityName.substring(entityName.indexOf(".") + 1, entityName.length());
            }
        }
        entityName = entityName.replaceAll("[^A-Za-z0-9]", ""); // remove all non-digits/alphanumeric

        if (clazz != null && EntityLiving.class.isAssignableFrom(clazz)) {
            if (this.debug) {
                this.envLog.logger.info("Attempting to find a valid type for " + entityName + "...");
            }
            EnumCreatureType creatureType = null;
            SpawnListEntry spawnListEntry = CustomSpawner.defaultSpawnListEntryMap.get(clazz.getName());
            if (spawnListEntry == null) {
                spawnListEntry = new SpawnListEntry((Class<? extends EntityLiving>) clazz, 8, 2, 3);
            }
            // temp fix for MoCreature ambients
            if (((EntityAnimal.class.isAssignableFrom(clazz) && !entityliving.isCreatureType(EnumCreatureType.AMBIENT, false)) || entityliving
                    .isCreatureType(EnumCreatureType.CREATURE, false))) //&& !(MoCEntityAmbient.class.isAssignableFrom(clazz)))
            {
                creatureType = EnumCreatureType.CREATURE;
                entityData = new EntityData(this, spawnListEntry, entityName, entityliving.getEntityId(), creatureType);
            } else if (((IMob.class.isAssignableFrom(clazz) || IRangedAttackMob.class.isAssignableFrom(clazz)) && (clazz != EntityMob.class))
                    || entityliving.isCreatureType(EnumCreatureType.MONSTER, false)) {
                creatureType = EnumCreatureType.MONSTER;
                entityData = new EntityData(this, spawnListEntry, entityName, entityliving.getEntityId(), creatureType);
            } else if (EntityAmbientCreature.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.AMBIENT, false)) {
                creatureType = EnumCreatureType.AMBIENT;
                entityData = new EntityData(this, spawnListEntry, entityName, entityliving.getEntityId(), creatureType);
            } else if (EntityWaterMob.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.WATER_CREATURE, false)) {
                creatureType = EnumCreatureType.WATER_CREATURE;
                entityData = new EntityData(this, spawnListEntry, entityName, entityliving.getEntityId(), creatureType);
            } else if (clazz != EntityLiving.class && clazz != EntityMob.class) {
                entityData = new EntityData(this, spawnListEntry, entityName, entityliving.getEntityId(), creatureType);
                entityData.setCanSpawn(false); // dont allow undefined types to spawn
            } else if (entityData == null) {
                if (this.debug) {
                    this.envLog.logger.info("Could not find a valid type for Entity " + entityName + " with class " + clazz + ", skipping...");
                }
                return null;
            }
            if (this.debug) {
                this.envLog.logger.info("Detected type as " + (creatureType == null ? creatureType : creatureType.name().toUpperCase()) + ".");
            }

            entityData.setType(creatureType);
            entityData.setLivingSpawnType(creatureType); // set default type
            entityData.setEntityName(entityName);
            entityData.setEntityID(entityliving.getEntityId());
            try {
                entityData.setMaxInChunk(entityliving.getMaxSpawnedInChunk()); // pixelmon crashes here
            } catch (Throwable e) {
                // ignore
            }
            if (this.debug) {
                this.envLog.logger.info("Added " + ((creatureType == null) ? "UNDEFINED" : creatureType.toString().toUpperCase()) + " " + clazz
                        + " with name " + entityName);
            }
            // handle frequencies
            // assign config for type of mod
            String entityClass = clazz.getName().toLowerCase();
            CMSConfiguration entityConfig = null;//= entityModMap.get("undefined").getModConfig();

            boolean undefined = true;
            if (this.debug) {
                this.envLog.logger.info("Attempting to detect mod for Entity " + entityName + "...");
            }

            if (entityClass.contains("net.minecraft.entity") || entityClass.toString().length() <= 3) // vanilla
            {
                EntityModData modData = this.entityModMap.get("vanilla");
                if (this.debug) {
                    this.envLog.logger.info("Matched mod Vanilla to " + entityName);
                }
                entityData.setEntityMod(modData);
                entityConfig = modData.getModConfig();
                entityData.setEntityConfig(entityConfig);
                processEntityConfig(entityData);
                if (!modData.addCreature(entityData)) {
                    entityData = modData.getCreature(entityData.getLivingSpawnType(), entityName);
                    entityData.setEntityID(entityliving.getEntityId());
                    entityData.setEntityMod(modData);
                }
                undefined = false;
            } else { // custom
                for (Map.Entry<String, EntityModData> modEntry : this.entityModMap.entrySet()) {
                    if (entityClass.contains(modEntry.getKey())) {
                        // Found match, use config
                        EntityModData modData = modEntry.getValue();
                        if (this.debug) {
                            this.envLog.logger.info("Matched mod " + modEntry.getKey() + " to " + entityClass);
                        }
                        entityData.setEntityMod(modData);
                        entityConfig = modData.getModConfig();
                        entityData.setEntityConfig(entityConfig);
                        processEntityConfig(entityData);
                        if (!modData.addCreature(entityData)) {
                            entityData = modData.getCreature(entityData.getLivingSpawnType(), entityName);
                            entityData.setEntityID(entityliving.getEntityId());
                            entityData.setEntityMod(modData);
                        }
                        undefined = false;
                        break;
                    }
                }
            }
            if (undefined) {
                if (this.debug) {
                    this.envLog.logger.info("Detected Undefined Entity Class " + entityClass
                            + ". You must add a mapping for this class in MoCGlobal.cfg.");
                }

                // no mapping for class in config so lets generate one
                String modKey = CMSUtils.generateModPackage(entityClass);
                if (!modKey.equals("")) {
                    String configName = modKey + ".cfg";

                    if (!entityModMap.containsKey(modKey)) {
                        this.entityModMap.put(modKey,
                                new EntityModData(modKey, modKey, new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                                        CREATURES_FILE_PATH + configName))));
                        if (this.debug) {
                            this.envLog.logger.info("Added Automatic Mod Entity Mapping " + modKey + " to file " + configName);
                        }
                        CMSConfigCategory modMapCat = this.CMSEnvironmentConfig.getCategory(CATEGORY_MOD_MAPPINGS);
                        modMapCat.put(modKey, new CMSProperty(modKey, new ArrayList<String>(Arrays.asList(modKey.toUpperCase(), configName)),
                                CMSProperty.Type.STRING, "automatically generated"));
                    }

                    EntityModData modData = this.entityModMap.get(modKey);
                    if (this.debug) {
                        this.envLog.logger.info("Matched mod " + modKey + " to " + entityClass);
                    }
                    entityData.setEntityMod(modData);
                    entityConfig = modData.getModConfig();
                    entityData.setEntityConfig(entityConfig);
                    processEntityConfig(entityData);
                    if (!modData.addCreature(entityData)) {
                        entityData = modData.getCreature(entityData.getLivingSpawnType(), entityName);
                        entityData.setEntityID(entityliving.getEntityId());
                        entityData.setEntityMod(modData);
                    }
                } else {
                    if (this.debug) {
                        this.envLog.logger.info("Could not generate an automatic mod mapping for entity " + entityClass);
                    }
                    return null;
                }
            }
            this.classToEntityMapping.put(clazz, entityData); // store for structure use
            this.entityMap.put(entityData.getEntityMod().getModTag() + "|" + entityData.getEntityName(), entityData); // used for fast lookups
            return entityData;
        }
        return null;
    }

    public void processEntityConfig(EntityData entityData) {
        String entityName = entityData.getEntityName();
        CMSConfigCategory entityCategory = null;
        // make sure the latest values of configs is loaded into memory before processing it
        entityData.getEntityConfig().load();

        if (!entityData.getEntityConfig().hasCategory(entityName.toLowerCase()))//getCategory(CATEGORY_ENTITY_SPAWN_SETTINGS).containsKey(entityName))
        {
            // generate default entity settings
            SpawnListEntry spawnlistentry = CustomSpawner.defaultSpawnListEntryMap.get(entityData.getEntityClass().getName());
            entityCategory = entityData.getEntityConfig().getCategory(entityName.toLowerCase());
            entityCategory.put("type", new CMSProperty("type",
                    entityData.getType() != null ? entityData.getType().name().toUpperCase() : "UNDEFINED", CMSProperty.Type.STRING));
            entityCategory.put("canSpawn", new CMSProperty("canSpawn", Boolean.toString(entityData.getCanSpawn()), CMSProperty.Type.BOOLEAN));
            entityCategory.put("frequency",
                    new CMSProperty("frequency", Integer.toString(spawnlistentry != null ? spawnlistentry.itemWeight : entityData.getFrequency()),
                            CMSProperty.Type.INTEGER));
            entityCategory.put("minSpawn",
                    new CMSProperty("minSpawn", Integer.toString(spawnlistentry != null ? spawnlistentry.minGroupCount : entityData.getMinSpawn()),
                            CMSProperty.Type.INTEGER));
            entityCategory.put("maxSpawn",
                    new CMSProperty("maxSpawn", Integer.toString(spawnlistentry != null ? spawnlistentry.maxGroupCount : entityData.getMaxSpawn()),
                            CMSProperty.Type.INTEGER));
            entityCategory.put("maxChunk", new CMSProperty("maxChunk", Integer.toString(entityData.getMaxInChunk()), CMSProperty.Type.INTEGER));
            entityCategory.put("minSpawnHeight", new CMSProperty("minSpawnHeight", Integer.toString(entityData.getMinSpawnHeight()),
                    CMSProperty.Type.INTEGER));
            entityCategory.put("maxSpawnHeight", new CMSProperty("maxSpawnHeight", Integer.toString(entityData.getMaxSpawnHeight()),
                    CMSProperty.Type.INTEGER));
            entityCategory.put("minLightlevel", new CMSProperty("minLightlevel", Integer.toString(entityData.getMinLightLevel()),
                    CMSProperty.Type.INTEGER));
            entityCategory.put("maxLightlevel", new CMSProperty("maxLightlevel", Integer.toString(entityData.getMaxLightLevel()),
                    CMSProperty.Type.INTEGER));
            entityCategory.put("opaqueBlock", new CMSProperty("opaqueBlock", entityData.getOpaqueBlock() == null ? "all" : entityData
                    .getOpaqueBlock().toString(), CMSProperty.Type.STRING));
            entityCategory.put("spawnBlockBlacklist", new CMSProperty("spawnBlockBlacklist", entityData.getSpawnBlockBlacklist(),
                    CMSProperty.Type.STRING));
            //entityData.getEntityConfig().get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityName, new ArrayList(Arrays.asList( entityData.getLivingSpawnType().getLivingSpawnTypeName(), new Integer(defaultSpawnListEntryMap.get(clazz.getName()) == null ? frequency : defaultSpawnListEntryMap.get(clazz.getName()).itemWeight).toString(), new Integer(defaultSpawnListEntryMap.get(clazz.getName()) == null ? minGroup : defaultSpawnListEntryMap.get(clazz.getName()).minGroupCount).toString(), new Integer(defaultSpawnListEntryMap.get(clazz.getName()) == null ? maxGroup : defaultSpawnListEntryMap.get(clazz.getName()).maxGroupCount).toString(), new Integer(entityData.getMaxInChunk()).toString())));
            entityData.getEntityConfig().save();
        } else {
            entityCategory = entityData.getEntityConfig().getCategory(entityName.toLowerCase());

            for (Map.Entry<String, CMSProperty> propEntry : entityCategory.getValues().entrySet()) {
                // handle entity config
                CMSProperty property = propEntry.getValue();

                if (property != null) {
                    if (propEntry.getKey().equalsIgnoreCase("type")) {
                        entityData.setLivingSpawnType(this.entitySpawnTypes.get(property.value.toUpperCase()));
                    } else if (propEntry.getKey().equalsIgnoreCase("canSpawn")) {
                        entityData.setCanSpawn(Boolean.parseBoolean(property.value));
                        if (entityData.getLivingSpawnType().getSpawnCap() == 0 || !entityData.getLivingSpawnType().allowSpawning()) {
                            entityData.setCanSpawn(false);
                        }
                        if (entityData.getLivingSpawnType().name().equalsIgnoreCase("UNDEFINED") || entityData.getFrequency() <= 0
                                || entityData.getMaxSpawn() <= 0 || entityData.getMaxInChunk() <= 0) {
                            entityData.setCanSpawn(false);
                        }
                    } else if (propEntry.getKey().equalsIgnoreCase("frequency")) {
                        entityData.setFrequency(Integer.parseInt(property.value));
                    } else if (propEntry.getKey().equalsIgnoreCase("minSpawn")) {
                        entityData.setMinSpawn(Integer.parseInt(property.value));
                    } else if (propEntry.getKey().equalsIgnoreCase("maxSpawn")) {
                        entityData.setMaxSpawn(Integer.parseInt(property.value));
                    } else if (propEntry.getKey().equalsIgnoreCase("maxChunk")) {
                        entityData.setMaxInChunk(Integer.parseInt(property.value));
                    } else if (propEntry.getKey().equalsIgnoreCase("minLightLevel")) {
                        entityData.setMinLightLevel(Integer.parseInt(property.value));
                    } else if (propEntry.getKey().equalsIgnoreCase("opaqueBlock")) {
                        if (property.value.equalsIgnoreCase("all")) {
                            entityData.setOpaqueBlock(null);
                        } else {
                            entityData.setOpaqueBlock(Boolean.parseBoolean(property.value));
                        }
                    } else if (propEntry.getKey().equalsIgnoreCase("spawnblockblacklist") && property.valueList != null) {
                        for (int i = 0; i < property.valueList.size(); i++) {
                            String bannedBlock = property.valueList.get(i);
                            // check for single id
                            try {
                                if (bannedBlock.indexOf("-") == -1) // check for ID without meta
                                {
                                    int blockID = Integer.parseInt(bannedBlock);
                                    entityData.addSpawnBlockToBanlist(Integer.toString(blockID));
                                    //System.out.println("Added spawnblock ID " + bannedBlock + " to blacklist.");
                                    if (this.debug) {
                                        this.envLog.logger.info("Added spawnblock ID " + bannedBlock + " to blacklist.");
                                    }
                                } else // blockID with meta
                                {
                                    int blockID = Integer.parseInt(bannedBlock.substring(0, bannedBlock.indexOf("-")));
                                    int blockMeta = Integer.parseInt(bannedBlock.substring(bannedBlock.indexOf("-"), bannedBlock.length()));
                                    String block = Integer.toString(blockID) + "-" + Integer.toString(blockMeta);
                                    entityData.addSpawnBlockToBanlist(block);
                                    //System.out.println("Added spawnblock ID-Meta " + bannedBlock + " to blacklist.");
                                    if (this.debug) {
                                        this.envLog.logger.info("Added spawnblock ID-Meta " + bannedBlock + " to blacklist.");
                                    }
                                }
                            } catch (Throwable e) {
                                if (this.debug) {
                                    this.envLog.logger.info("Failed to blacklist spawnblock " + bannedBlock
                                            + ", invalid format. Format needs to be id-meta");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void initializeBiomes() {
        for (int i = 0; i < Biome.getBiomeGenArray().length; i++) {
            Biome biome = Biome.getBiomeGenArray()[i];
            if (biome == null) {
                continue;
            }
            String biomeName = biome.biomeName;
            String biomeClass = biome.getClass().getName();
            BiomeData biomeData = new BiomeData(biome);
            Type[] types = BiomeDictionary.getTypesForBiome(biome);
            biomeData.setTypes(types);
            if (this.debug) {
                this.envLog.logger.info("Detected Biome " + biomeName + " with class " + biomeClass + " with biomeID " + biome.biomeID
                        + " with types " + types);
            }
            boolean found = false;
            for (Map.Entry<String, BiomeModData> modEntry : this.biomeModMap.entrySet()) {
                if (biomeClass.contains(modEntry.getKey())) {
                    // Found match, use config
                    BiomeModData modData = modEntry.getValue();
                    biomeData.setTag(modData.getModTag()); // needed for undefined
                    biomeData.setDefined((true));
                    modData.addBiome(biomeData);
                    found = true;
                    break;
                } else if ((biomeClass.contains("net.minecraft") || biomeClass.length() <= 3) && modEntry.getKey().equalsIgnoreCase("vanilla")) // special case for vanilla
                {
                    if (this.debug) {
                        this.envLog.logger.info("Matched mod " + modEntry.getKey() + " to " + biomeClass);
                    }
                    BiomeModData modData = modEntry.getValue();
                    biomeData.setTag(modData.getModTag());
                    biomeData.setDefined((true));
                    modData.addBiome(biomeData);
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (this.debug) {
                    this.envLog.logger.info("Detected Undefined Biome Class " + biomeClass
                            + ". Generating automatic mapping for this class in Environment.cfg ...");
                }

                // no mapping for class in config so lets generate one
                String modKey = CMSUtils.generateModPackage(biomeClass);
                if (!modKey.equals("")) {
                    String configName = modKey + ".cfg";

                    BiomeModData modData =
                            new BiomeModData(modKey, modKey, new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                                    BIOMES_FILE_PATH + configName)));
                    if (this.debug) {
                        this.envLog.logger.info("Added Automatic Mod Biome Mapping " + modKey + " with tag " + modKey + " to file " + configName);
                    }
                    CMSConfigCategory modMapCat = this.CMSEnvironmentConfig.getCategory(CATEGORY_MOD_MAPPINGS);
                    modMapCat.put(modKey, new CMSProperty(modKey, new ArrayList<String>(Arrays.asList(modKey.toUpperCase(), configName)),
                            CMSProperty.Type.STRING, "automatically generated"));
                    biomeData.setTag(modData.getModTag());
                    biomeData.setDefined((true));
                    modData.addBiome(biomeData);
                    this.biomeModMap.put(modKey, modData);
                }
            }
        }
        // save configs
        for (Map.Entry<String, BiomeModData> modEntry : this.biomeModMap.entrySet()) {
            for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
                BiomeModData modData = modEntry.getValue();
                CMSProperty prop = modData.getModConfig().get("biomegroups", "biomegroups");
                if (prop != null && prop.valueList != null) {
                    prop.valueList = modData.getBiomes();
                } else {
                    modData.getModConfig().get("biomegroups", type.name(), modData.getBiomesForType(type));
                }
                modData.getModConfig().save();
            }
        }

        this.CMSEnvironmentConfig
                .addCustomCategoryComment(
                        CATEGORY_MOD_MAPPINGS,
                        "Mod Biome Mappings\n"
                                + "You may change tag values but do NOT change the default keys since they are used to generate our defaults.\n"
                                + "For example, 'twilightforest=TL:TwilightForest.cfg' may be changed to 'twilightforest=TWL:TWL.cfg' but may NOT be changed to 'twilight=TWL:TWL.cfg'");
        // update tags in our defaults if necessary
        this.CMSEnvironmentConfig.save();
        initDefaultGroups();
    }

    public void initDefaultGroups() {
        CMSUtils.registerAllBiomes();
        // scan all biome mods for biome dictionary groups
        for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
            List<String> biomes = new ArrayList<String>();
            for (Biome biome : BiomeDictionary.getBiomesForType(type)) {
                for (Map.Entry<String, BiomeModData> modEntry : this.biomeModMap.entrySet()) {
                    BiomeModData biomeModData = modEntry.getValue();
                    if (biomeModData.hasBiome(biome)) {
                        biomes.add(biomeModData.getModTag() + "|" + biome.biomeName);
                        break;
                    }
                }
            }
            CMSProperty prop = new CMSProperty(type.name(), biomes, CMSProperty.Type.STRING);
            if (!biomes.isEmpty()) {
                if (this.biomeGroupMap.containsKey(type.name())) {
                    this.biomeGroupMap.remove(type.name());
                }
                this.biomeGroupMap.put(type.name(), new BiomeGroupData(type.name(), biomes));
                Collections.sort(biomes); // sort biome groups for GUI
                prop.valueList = biomes; // blood - make sure to link our newly generated list to the configuration list for direct modification later
                if (this.debug) {
                    this.envLog.logger.info("Successfully added Biome Group " + type.name());
                }
            }
            this.CMSEntityBiomeGroupsConfig.getCategory(CATEGORY_BIOMEGROUP_DEFAULTS).put(type.name(), prop);
            this.CMSEntityBiomeGroupsConfig.save();
        }
    }

    public void populateSpawnBiomes() {
        if (this.debug) {
            this.envLog.logger.info("Populating spawn biomes for environment " + this.name);
        }
        this.CMSEntityBiomeGroupsConfig.load();
        for (EntityData entityData : this.entityMap.values()) {
            if (this.debug) {
                this.envLog.logger.info("generating biome spawn list for entity " + entityData.getEntityName());
            }
            entityData.getEntityConfig().load();
            CMSConfigCategory entityCategory = entityData.getEntityConfig().getCategory(entityData.getEntityName().toLowerCase());
            // Add spawnable biomes for each entity
            if (entityCategory.containsKey("biomegroups"))// && entityBiomeCategory.get(entityName).valueList != null)
            {
                List<String> biomeGroups = entityCategory.get("biomegroups").valueList;
                for (int i = 0; i < biomeGroups.size(); i++) {
                    String biomeGroupName = biomeGroups.get(i);
                    List<Biome> spawnBiomes = new ArrayList<Biome>();
                    // check default entity biome config
                    if (this.CMSEntityBiomeGroupsConfig.getCategory(CATEGORY_BIOMEGROUP_DEFAULTS).containsKey(
                            entityData.getEntityMod().getModTag().toUpperCase() + "_" + biomeGroupName)) {
                        biomeGroupName = entityData.getEntityMod().getModTag().toUpperCase() + "_" + biomeGroups.get(i);
                        CMSProperty biomeProps = this.CMSEntityBiomeGroupsConfig.getCategory(CATEGORY_BIOMEGROUP_DEFAULTS).get(biomeGroupName);
                        for (int j = 0; j < biomeProps.valueList.size(); j++) {
                            List<String> biomeParts = CMSUtils.parseName(biomeProps.valueList.get(j));
                            BiomeModData biomeModData = CMSUtils.getBiomeModData(this.biomeModMap, biomeParts.get(0));
                            if (biomeModData != null) {
                                if (this.debug) {
                                    this.envLog.logger.info("adding spawn biome " + biomeProps.valueList.get(j) + " for entity "
                                            + entityData.getEntityName());
                                }
                                spawnBiomes.add(biomeModData.getBiome(biomeProps.valueList.get(j)));
                                entityData.addSpawnBiome(biomeModData.getBiome(biomeProps.valueList.get(j)));
                            }
                        }
                        entityData.addBiomeGroupSpawnMap(biomeGroupName, spawnBiomes);
                    } else // search for group in biome mod configs
                    {
                        for (Map.Entry<String, BiomeModData> modEntry : this.biomeModMap.entrySet()) {
                            BiomeModData biomeModData = modEntry.getValue();
                            CMSConfigCategory cat = biomeModData.getModConfig().getCategory("biomegroups");
                            if (cat.containsKey(biomeGroupName)) {
                                CMSProperty biomeProps = cat.get(biomeGroupName);
                                for (int j = 0; j < biomeProps.valueList.size(); j++) {
                                    if (spawnBiomes.contains(biomeModData.getBiome(biomeModData.getModTag() + "|" + biomeProps.valueList.get(j)))) {
                                        continue;
                                    }
                                    Biome biome = biomeModData.getBiome(biomeModData.getModTag() + "|" + biomeProps.valueList.get(j));
                                    if (biome == null) {
                                        continue;
                                    }
                                    spawnBiomes.add(biome);
                                    entityData.addSpawnBiome(biome);
                                }
                            }
                        }
                        entityData.addBiomeGroupSpawnMap(biomeGroupName, spawnBiomes);
                    }
                }
                entityData.setBiomeGroups(biomeGroups);
            } else // populate empty list with vanilla entries
            {
                if (this.debug) {
                    this.envLog.logger.info("Could not find existing biomegroups for entity " + entityData.getEntityName()
                            + ", generating defaults...");
                }
                ArrayList<String> biomes = new ArrayList<String>();
                ArrayList<Biome> entryBiomes = CustomSpawner.entityDefaultSpawnBiomes.get(entityData.getEntityClass().getName());
                if (entryBiomes != null) {
                    for (int i = 0; i < entryBiomes.size(); i++) {
                        for (Map.Entry<String, BiomeModData> modEntry : this.biomeModMap.entrySet()) {
                            BiomeModData biomeModData = modEntry.getValue();
                            if (biomeModData.hasBiome(entryBiomes.get(i))) {
                                if (this.debug) {
                                    this.envLog.logger.info("Adding biome " + biomeModData.getModTag() + "|" + entryBiomes.get(i).biomeName
                                            + " to biomegroups for entity " + entityData.getEntityName() + " in environment " + name());
                                }
                                biomes.add(biomeModData.getModTag() + "|" + entryBiomes.get(i).biomeName);
                                entityData.addSpawnBiome(entryBiomes.get(i));
                            }
                        }
                    }
                } else {
                    if (this.debug) {
                        this.envLog.logger.info("No default biomes found for entity " + entityData.getEntityName());
                    }
                }
                CMSConfigCategory entityBiomeGroupCat = this.CMSEntityBiomeGroupsConfig.getCategory(CATEGORY_BIOMEGROUP_DEFAULTS);
                if (!entityBiomeGroupCat.containsKey(entityData.getEntityMod().getModTag() + "_" + entityData.getEntityName().toUpperCase()
                        + "_DEFAULT")) {
                    CMSProperty prop =
                            new CMSProperty(entityData.getEntityMod().getModTag() + "_" + entityData.getEntityName().toUpperCase() + "_DEFAULT",
                                    biomes, CMSProperty.Type.STRING);
                    this.CMSEntityBiomeGroupsConfig.getCategory(CATEGORY_BIOMEGROUP_DEFAULTS).put(
                            entityData.getEntityMod().getModTag() + "_" + entityData.getEntityName().toUpperCase() + "_DEFAULT", prop);
                    entityCategory.put("biomegroups",
                            new CMSProperty("biomegroups", new ArrayList<String>(Arrays.asList(entityData.getEntityName().toUpperCase() + "_DEFAULT")),
                                    CMSProperty.Type.STRING));
                    entityData.setBiomeGroups(prop.valueList);
                    this.biomeGroupMap.put(entityData.getEntityMod().getModTag() + "_" + entityData.getEntityName().toUpperCase() + "_DEFAULT",
                            new BiomeGroupData(entityData.getEntityMod().getModTag() + "_" + entityData.getEntityName().toUpperCase() + "_DEFAULT",
                                    biomes));
                }
            }
            // entity config comments
            // entityData.getEntityConfig().addCustomCategoryComment(CATEGORY_ENTITY_SPAWN_SETTINGS, "S:Name <Type:Frequency:MinSpawn:MaxSpawn:MaxSpawnInChunk>");
            entityData.getEntityConfig().save();
        }
        this.CMSEntityBiomeGroupsConfig.save();
    }

    /**
     * Populates spawn lists
     */
    public void populateSpawns() {
        if (this.debug) {
            this.envLog.logger.info("Populating spawns...");
        }

        if (this.debug) {
            this.envLog.logger.info("Scanning mod configs for entities...");
        }
        for (Map.Entry<String, EntityModData> modEntry : this.entityModMap.entrySet()) {
            for (EntitySpawnType entitySpawnType : this.entitySpawnTypes.values()) {
                if (entitySpawnType.name().equalsIgnoreCase("UNDEFINED") || modEntry.getValue().getSpawnListFromType(entitySpawnType) == null) {
                    continue;
                }

                for (Map.Entry<String, EntityData> entityEntry : modEntry.getValue().getSpawnListFromType(entitySpawnType).entrySet()) {
                    EntityData entityData = entityEntry.getValue();
                    if (entityData.getSpawnBiomes() != null && entityData.getSpawnBiomes().size() > 0 && entityData.getLivingSpawnType() != null) {
                        Biome[] biomesToSpawn = new Biome[entityData.getSpawnBiomes().size()];
                        biomesToSpawn = entityData.getSpawnBiomes().toArray(biomesToSpawn);
                        if (this.debug) {
                            this.envLog.logger.info(entityData.getEntityName() + " canSpawn = " + entityData.getCanSpawn());
                        }
                        if (entityData.getCanSpawn()) {
                            CustomSpawner.instance().AddCustomSpawn(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(),
                                    entityData.getMaxSpawn(), entityData.getLivingSpawnType(), biomesToSpawn);
                            if (this.debug) {
                                this.envLog.logger.info("Added " + entityData.getEntityClass() + " to CustomSpawner spawn lists");
                            }
                        } else {
                            if (this.debug) {
                                this.envLog.logger.info("Removing " + entityData.getEntityClass() + " from CustomSpawner spawn lists");
                            }
                            CustomSpawner.instance().RemoveCustomSpawn(entityData.getEntityClass(), entityData.getLivingSpawnType(), biomesToSpawn);
                        }
                        //otherwise the Forge spawnlist remains populated with duplicated entries on CMS
                        //removeAllBiomeSpawns(entityData, true); // If we add a entity to CMS, we MUST remove it from ALL biomes on vanilla to avoid massive spawning in missing biomes
                        // if (debug) envLog.logger.info("Removed " + entityData.getEntityClass() + " from Vanilla spawn lists");
                    } else {
                        if (this.debug) {
                            this.envLog.logger.info("Skipping " + entityData.getEntityClass() + " spawn!!" + ", spawnbiomes = "
                                    + entityData.getSpawnBiomes());
                        }
                    }
                }
            }
        }
    }

    public void genModConfiguration() {
        this.entityTypes.put("UNDEFINED", null);
        this.entityTypes.put("CREATURE", EnumCreatureType.CREATURE);
        this.entityTypes.put("MONSTER", EnumCreatureType.MONSTER);
        this.entityTypes.put("WATER_CREATURE", EnumCreatureType.WATER_CREATURE);
        this.entityTypes.put("AMBIENT", EnumCreatureType.AMBIENT);
        CMSConfigCategory modMapCat = this.CMSEnvironmentConfig.getCategory(CATEGORY_MOD_MAPPINGS);

        // setup defaults
        this.defaultModMap.put("vanilla", new EntityModData("vanilla", "MC", new CMSConfiguration(new File(
                this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "Vanilla.cfg"))));
        this.defaultModMap.put("undefined",
                new EntityModData("undefined", "U", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH
                        + "Undefined.cfg"))));
        if (Loader.isModLoaded("MoCreatures")) {
            this.defaultModMap.put("drzhark",
                    new EntityModData("drzhark", "MOC", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH
                            + "MoCreatures.cfg"))));
        }
        if (Loader.isModLoaded("BiomesOPlenty")) {
            this.defaultModMap.put("biomesop",
                    new EntityModData("biomesop", "BOP", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "BiomesOPlenty.cfg"))));
        }
        if (Loader.isModLoaded("BWG4")) {
            this.defaultModMap.put("ted80",
                    new EntityModData("ted80", "BWG", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH
                            + "BWG.cfg"))));
        }
        if (Loader.isModLoaded("ExtrabiomesXL")) {
            this.defaultModMap.put("extrabiomes",
                    new EntityModData("extrabiomes", "XL", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "ExtraBiomesXL.cfg"))));
        }
        if (Loader.isModLoaded("TwilightForest")) {
            this.defaultModMap.put("twilightforest", new EntityModData("twilightforest", "TF", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "TwilightForest.cfg"))));
        }
        if (Loader.isModLoaded("grimoiregaia")) {
            this.defaultModMap.put("gaia", new EntityModData("gaia", "GAIA", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "GrimoireOfGaia.cfg"))));
        }
        if (Loader.isModLoaded("InfernalMobs")) {
            this.defaultModMap.put("atomicstryker", new EntityModData("atomicstryker", "IM", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "InfernalMobs.cfg"))));
        }
        if (Loader.isModLoaded("arsmagica2")) {
            this.defaultModMap.put("arsmagica",
                    new EntityModData("arsmagica", "ARS", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "ArsMagica.cfg"))));
        }
        if (Loader.isModLoaded("ProjectZulu|Core") || Loader.isModLoaded("ProjectZulu|Mob")) {
            this.defaultModMap.put("projectzulu",
                    new EntityModData("projectzulu", "PZ", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "ProjectZulu.cfg"))));
        }
        if (Loader.isModLoaded("Thaumcraft")) {
            this.defaultModMap.put("thaumcraft",
                    new EntityModData("thaumcraft", "TC", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "Thaumcraft.cfg"))));
        }
        if (Loader.isModLoaded("Highlands")) {
            this.defaultModMap.put("highlands",
                    new EntityModData("highlands", "HL", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "Highlands.cfg"))));
        }
        if (Loader.isModLoaded("TConstruct")) {
            this.defaultModMap.put("tinker",
                    new EntityModData("tinker", "TC", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH
                            + "TinkerConstruct.cfg"))));
        }
        if (Loader.isModLoaded("Atum")) {
            this.defaultModMap.put("atum", new EntityModData("atum", "ATUM", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "Atum.cfg"))));
        }
        if (Loader.isModLoaded("AngryCreatures")) {
            this.defaultModMap.put("advancedglowstone", new EntityModData("advancedglowstone", "AC", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "AngryCreatures.cfg"))));
        }
        if (Loader.isModLoaded("MineFantasy")) {
            this.defaultModMap.put("minefantasy",
                    new EntityModData("minefantasy", "MF", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "MineFantasy.cfg"))));
        }
        if (Loader.isModLoaded("PrimitiveMobs")) {
            this.defaultModMap.put("primitivemobs", new EntityModData("primitivemobs", "PM", new CMSConfiguration(new File(
                    this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + "PrimitiveMobs.cfg"))));
        }
        if (Loader.isModLoaded("AtmosMobs")) {
            this.defaultModMap.put("atmosmobs",
                    new EntityModData("atmosmobs", "ATMOS", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "AtmosMobs.cfg"))));
        }
        if (Loader.isModLoaded("farlanders")) {
            this.defaultModMap.put("farlanders",
                    new EntityModData("farlanders", "FL", new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(),
                            CREATURES_FILE_PATH + "Farlanders.cfg"))));
        }

        // generate default key mappings
        for (Map.Entry<String, EntityModData> modEntry : this.defaultModMap.entrySet()) {
            List<String> values =
                    new ArrayList<String>(Arrays.asList(modEntry.getValue().getModTag(), modEntry.getValue().getModConfig().getFileName() + ".cfg"));
            modMapCat.put(modEntry.getKey(), new CMSProperty(modEntry.getKey(), values, CMSProperty.Type.STRING));
            this.biomeModMap.put(modEntry.getKey(), new BiomeModData(modEntry.getKey(), modEntry.getValue().getModTag(), new CMSConfiguration(
                    new File(this.CMSEnvironmentConfig.file.getParent(), BIOMES_FILE_PATH
                            + (modEntry.getValue().getModConfig().getFileName() + ".cfg")))));
            this.entityModMap.put(modEntry.getKey(), new EntityModData(modEntry.getKey(), modEntry.getValue().getModTag(), new CMSConfiguration(
                    new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH
                            + (modEntry.getValue().getModConfig().getFileName() + ".cfg")))));
            if (this.debug) {
                this.envLog.logger.info("Added Mod Entity Mapping " + modEntry.getKey() + " to file "
                        + (modEntry.getValue().getModConfig().getFileName() + ".cfg"));
            }
            if (this.debug) {
                this.envLog.logger.info("Added Mod Biome Mapping " + modEntry.getKey() + " with tag " + modEntry.getValue().getModTag() + " to file "
                        + (modEntry.getValue().getModConfig().getFileName() + ".cfg"));
            }
        }
        // handle custom tag to config mappings
        for (Map.Entry<String, CMSProperty> propEntry : modMapCat.entrySet()) {
            CMSProperty prop = propEntry.getValue();
            if (prop != null && !this.biomeModMap.containsKey(propEntry.getKey())) {
                if (prop.valueList.size() == 2) {
                    this.biomeModMap.put(propEntry.getKey(), new BiomeModData(propEntry.getKey(), prop.valueList.get(0), new CMSConfiguration(
                            new File(this.CMSEnvironmentConfig.file.getParent(), BIOMES_FILE_PATH + prop.valueList.get(1)))));
                    if (this.debug) {
                        this.envLog.logger.info("Added Custom Mod Biome Mapping " + propEntry.getKey() + " with tag " + prop.valueList.get(0)
                                + " to file " + prop.valueList.get(1));
                    }
                    this.entityModMap.put(propEntry.getKey(), new EntityModData(propEntry.getKey(), prop.valueList.get(0), new CMSConfiguration(
                            new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + prop.valueList.get(1)))));
                    if (this.debug) {
                        this.envLog.logger.info("Added Custom Mod Entity Mapping " + propEntry.getKey() + " with tag " + prop.valueList.get(0)
                                + " to file " + prop.valueList.get(1));
                    }
                }
            }
        }

        this.CMSEnvironmentConfig.save();
    }

    /**
     * Reads values from file
     */
    public void readConfigValues() {
        // general
        this.worldGenCreatureSpawning =
                this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "worldGenCreatureSpawning", true,
                        "Allows spawns during world chunk generation.").getBoolean(true);
        this.minDespawnLightLevel =
                this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "minDespawnLightLevel", this.minDespawnLightLevel,
                        "The minimum light level threshold used to determine whether or not to despawn a farm animal.").getInt();
        this.maxDespawnLightLevel =
                this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxDespawnLightLevel", this.minDespawnLightLevel,
                        "The maximum light level threshold used to determine whether or not to despawn a farm animal.").getInt();
        this.killallUseLightLevel =
                this.CMSEnvironmentConfig
                        .get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "killallUseLightLevel", true,
                                "Turns on check for lightLevel before killing an entity during a killall. If entity is under lightLevel threshold, it will be killed.")
                        .getBoolean(false);
        this.enforceMaxSpawnLimits =
                this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "enforceMaxSpawnLimits", false,
                        "If enabled, all spawns will stop when max spawn limits have been reached for type.").getBoolean(false);
        this.debug =
                this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "debug", false, "Turns on CustomMobSpawner debug logging.")
                        .getBoolean(false);
        this.killallVillagers = this.CMSEnvironmentConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "killAllVillagers", false).getBoolean(false);
        this.forceDespawns =
                this.CMSEnvironmentConfig
                        .get(CATEGORY_CUSTOMSPAWNER_SETTINGS,
                                "forceDespawns",
                                false,
                                "If true, Custom Spawner will attempt to despawn all creatures including vanilla. It will attempt to prevent despawning of villagers, tamed creatures, and farm animals. The purpose of this setting is to provide a more dynamic experience.")
                        .getBoolean(false);
        this.CMSEnvironmentConfig.save();
    }
}
