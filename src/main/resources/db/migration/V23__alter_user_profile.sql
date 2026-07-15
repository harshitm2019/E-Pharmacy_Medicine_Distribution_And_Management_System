ALTER TABLE user_profile
DROP CONSTRAINT user_profile_user_id_fkey;

ALTER TABLE user_profile
ADD CONSTRAINT user_profile_user_id_fkey
FOREIGN KEY (user_id)
REFERENCES users(user_id)
ON DELETE CASCADE;