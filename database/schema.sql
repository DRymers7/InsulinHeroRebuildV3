BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, user_data, user_measurements, insulin_information, insulin, insulin_user_data_join, 
meals, meal_information, meals_user_join, blood_sugar, blood_sugar_user_data_join, dose,  CASCADE;
DROP SEQUENCE IF EXISTS seq_user_id, seq_insulin_id, seq_meal_id, seq_blood_sugar_id, seq_dose_id CASCADE;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_insulin_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_meal_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_blood_sugar_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_dose_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE TABLE users (
	
user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
username varchar(255) NOT NULL UNIQUE,
password_hash varchar(200) NOT NULL,
role varchar(50) NOT NULL,

CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- insulin type
-- a1c (measure of how much blood cells coated with sugar (percentage to tenths))
-- sugar remains on blood cells for three months measure of average blood sugar (user input)

CREATE TABLE user_data (
	
user_id int NOT NULL PRIMARY KEY,
a1c decimal(4, 2),
target_low int NOT NULL,
target_high int NOT NULL,
fasting_glucose int,
diabetes_type int,
prediabetic varchar(16),
user_age int,
weight_kg int,
height_meters int,
daily_carb_goal int,
daily_calorie_goal int,
avg_glycemic_index_goal decimal(4,2),
emergency_contact_1 varchar(32),
emergency_contact_2 varchar(32),
profile_pic TEXT,
date_last_updated DATE DEFAULT CURRENT_DATE,
time_last_updated TIME DEFAULT CURRENT_TIME,
-- bmi (weight/height)**2 calc on server

CONSTRAINT FK_user_data_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE user_measurements (
	
user_id int NOT NULL PRIMARY KEY,
regular_insulin_dose decimal(4,2),
NPH_insulin_dose decimal(4,2),
ultralente_insulin_dose decimal(4,2),
unspecified_blood_glucose_measurement decimal(4,2),
pre_breakfast_blood_glucose decimal(4,2),
post_breakfast_blood_glucose decimal(4,2),
pre_lunch_blood_glucose decimal(4,2),
post_lunch_blood_glucose decimal(4,2),
pre_supper_blood_glucose decimal(4,2),
post_supper_blood_glucose decimal(4,2),
pre_snack_blood_glucose decimal(4,2),
hypoglycemic_symptoms varchar(255),
typical_meal_ingestion varchar(255),
more_than_usual_meal_ingestion varchar(255),
less_than_usual_meal_ingestion varchar(255),
typical_exercise_activity varchar(255),
more_than_usual_exercise varchar(255),
less_than_usual_exercise varchar(255),
unspecified_special_event varchar(255),
	
CONSTRAINT FK_user_data_user FOREIGN KEY (user_id) REFERENCES user_data(user_id)
);

-- divide into smaller schema

CREATE TABLE insulin_information (
insulin_brand_name varchar(32) NOT NULL PRIMARY KEY,
insulin_type varchar(32) NOT NULL,
half_life int, --minutes
onset_low int, --display information for when to check blood sugar
onset_high int,
peak int,
duration int
);

CREATE TABLE insulin (
	
insulin_id int DEFAULT nextval('seq_insulin_id'::regclass) NOT NULL unique,
base_level decimal(10,3),
avg_level decimal(10,3),
insulin_brand_name varchar(32),
insulin_strength varchar(32),
insulin_ratio decimal(3,2) NOT NULL,
time_last_dose TIME DEFAULT CURRENT_TIME,
date_last_dose DATE DEFAULT CURRENT_DATE,

CONSTRAINT FK_insulin_insulin_information FOREIGN KEY (insulin_brand_name) REFERENCES insulin_information(insulin_brand_name)
);

CREATE TABLE insulin_user_data_join (
	
user_id int,
insulin_id int,

CONSTRAINT PK_insulin_user_data_join PRIMARY KEY (user_id, insulin_id),
CONSTRAINT FK_insulin_user_data_join_insulin FOREIGN KEY (insulin_id) REFERENCES insulin(insulin_id),
CONSTRAINT FK_insulin_user_data_join_user_data FOREIGN KEY (user_id) REFERENCES user_data(user_id)
);

CREATE TABLE meals (
	
meal_id int DEFAULT nextval('seq_meal_id'::regclass) NOT NULL unique,
serving_size_carbs decimal(3,2),
food_name varchar (32),
time_of_meal TIME DEFAULT CURRENT_TIME,
date_of_meal DATE DEFAULT CURRENT_DATE
);

CREATE TABLE meal_information (
	
meal_id int NOT NULL PRIMARY KEY,
glycemic_load decimal(3,2), --FDA API,
calories_g decimal(5,2),
total_fats_g decimal(5,2),
saturated_fats_g decimal(5,2),
trans_fats_g decimal(5,2),
fatty_acids_monosaturated_g decimal(5,2),
fatty_acids_polyunsaturated_g decimal(5,2),
carbs_by_difference_g decimal(5,2),
total_carbs_g decimal(5,2),
fiber_g decimal(5,2),
sugars_g decimal(5,2),
added_sugars_g decimal(5,2),
protein_g decimal(5,2),
cholesterol_mg decimal(5,2),
sodium_mg decimal(5,2),
calcium_mg decimal(5,2),
magnesium_mg decimal(5,2),
potassium_mg decimal(5,2),
iron_mg decimal(5,2),
zinc_mg decimal(5,2),
phosphorous_mg decimal(5,2),
vit_A_RAE_micg decimal(5,2),
vit_C_mg decimal(5,2),
thiamin_mg decimal(5,2),
riboflavin_mg decimal(5,2),
niacin_mg decimal(5,2),
vit_b6_mg decimal(5,2),
folate_DFE_micg decimal(5,2),
folate_food_micg decimal(5,2),
folic_acid_micg decimal(5,2),
vit_b12_micg decimal(5,2),
vit_d_micg decimal(5,2),
vit_e_micg decimal(5,2),
vit_k_micg decimal(5,2),
water_g decimal(5,2),
	
CONSTRAINT FK_meal_information_meals FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
);

CREATE TABLE meals_user_join (
	
meal_id int NOT NULL,
user_id int NOT NULL,

CONSTRAINT PK_meals_user_join PRIMARY KEY (meal_id, user_id),
CONSTRAINT FK_meals_user_join_user_data FOREIGN KEY (user_id) REFERENCES user_data(user_id),
CONSTRAINT FK_meals_user_join_meals FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
);

CREATE TABLE blood_sugar (
	
blood_sugar_id int DEFAULT nextval('seq_blood_sugar_id'::regclass) NOT NULL unique,
input_level int NOT NULL,
time_last_measurement TIME DEFAULT CURRENT_TIME,
date_last_measurement DATE DEFAULT CURRENT_DATE
);

CREATE TABLE blood_sugar_user_data_join (
	
blood_sugar_id int NOT NULL,
user_id int NOT NULL,

CONSTRAINT PK_blood_sugar_user_data_join PRIMARY KEY (blood_sugar_id, user_id),
CONSTRAINT FK_blood_sugar_user_data_join_user_data FOREIGN KEY (user_id) REFERENCES user_data(user_id),
CONSTRAINT FK_blood_sugar_user_data_join_blood_sugar FOREIGN KEY (blood_sugar_id) REFERENCES blood_sugar(blood_sugar_id)
);

CREATE TABLE dose (
	
blood_sugar_id int NOT NULL PRIMARY KEY,
input_level int, 
type_of_dose varchar(32),
time_of_dose TIME DEFAULT CURRENT_TIME,
date_of_dose DATE DEFAULT CURRENT_DATE,

CONSTRAINT FK_dose_blood_sugar FOREIGN KEY (blood_sugar_id) REFERENCES blood_sugar(blood_sugar_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user@example.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('rymersd@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO user_data (user_id, a1c, target_low, target_high, fasting_glucose, diabetes_type, prediabetic, user_age, weight_kg, height_meters, daily_carb_goal,
daily_calorie_goal, avg_glycemic_index_goal, emergency_contact_1, emergency_contact_2, date_last_updated, time_last_updated)
VALUES (1, 5.7, 80, 130, 99, 2, 'No', 37, 75, 1.77, 225, 2000, 55, 'rymersd@gmail.com', 'mdelafay497@gmail.com', '2022/12/21', '12:00');

INSERT INTO user_data (user_id, a1c, target_low, target_high, fasting_glucose, diabetes_type, prediabetic, user_age, weight_kg, height_meters, daily_carb_goal,
daily_calorie_goal, avg_glycemic_index_goal, emergency_contact_1, emergency_contact_2, date_last_updated, time_last_updated)
VALUES (3, 5.7, 80, 130, 99, 0, 'Yes', 37, 75, 1.77, 225, 2000, 55, 'rymersd@gmail.com', 'mdelafay497@gmail.com', '2022/12/21', '12:00');

INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Admelog', 'Rapid-Acting', 51, 15, 15, 60, 180);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Apidra', 'Rapid-Acting', 42, 15, 15, 60, 180);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Fiasp', 'Rapid-Acting', 57, 15, 15, 60, 180);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Humalog', 'Rapid-Acting', 60, 15, 15, 60, 180);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Novolog', 'Rapid-Acting', 81, 15, 15, 60, 180);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Humulin R', 'Short-Acting', 90, 30, 30, 150, 270);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Novolin R', 'Short-Acting', 90, 30, 30, 150, 270);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Humulin N', 'Intermediate-Acting', 264, 180, 180, 1080, 900);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, peak, duration)
VALUES ('Novolin N', 'Intermediate-Acting', 264, 180, 180, 1080, 900);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, duration)
VALUES ('Basaglar KwikPen', 'Long-Acting', 720, 180, 180, 1440);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, duration)
VALUES ('Lantus', 'Long-Acting', 810, 180, 180, 1440);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, duration)
VALUES ('Levemir', 'Long-Acting', 720, 180, 180, 1440);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, duration)
VALUES ('Toujeo', 'Long-Acting', 1110, 180, 180, 1440);
INSERT INTO insulin_information (insulin_brand_name, insulin_type, half_life, onset_low, onset_high, duration)
VALUES ('TresibaFlexTouch', 'Long-Acting', 810, 180, 180, 1440);
INSERT INTO insulin (base_level, avg_level, time_last_dose, insulin_brand_name, insulin_strength, insulin_ratio)
VALUES (10.0, 10.0, '2022/12/12 00:00:00', 'Admelog', 'Strong', 0.14);
INSERT INTO insulin (base_level, avg_level, time_last_dose, insulin_brand_name, insulin_strength, insulin_ratio)
VALUES (10.0, 10.0, '2022/12/12 00:00:00', 'Apidra', 'Strong', 0.14);
INSERT INTO insulin (base_level, avg_level, time_last_dose, insulin_brand_name, insulin_strength, insulin_ratio)
VALUES (10.0, 10.0, '2022/12/12 00:00:00', 'Fiasp', 'Strong', 0.14);
INSERT INTO insulin (base_level, avg_level, time_last_dose, insulin_brand_name, insulin_strength, insulin_ratio)
VALUES (10.0, 10.0, '2022/11/11 00:00:00', 'Fiasp', 'Strong', 0.14);

INSERT INTO meals (serving_size_carbs, food_name, time_of_meal, date_of_meal) VALUES (2.1, 'test', '12:00', current_date);
INSERT INTO meals_user_join (meal_id, user_id) VALUES (1, 1);

COMMIT TRANSACTION