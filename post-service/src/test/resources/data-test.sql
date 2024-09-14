-- users
insert into public.users (id, public_id, user_name, deleted, contact_email, first_name) values (1,'bb874ce2-dc46-4f11-8915-c1d644f236df','testuser',false,'admin@gmail.com','peterKason');

-- posts
insert into posts.tb_posts(post_id, title, content, public_id, deleted, created_by_id) values (100, 'post 1', 'test post one', 'ab874ce2-dc46-4f11-8915-c1d644f236da', false, 1);