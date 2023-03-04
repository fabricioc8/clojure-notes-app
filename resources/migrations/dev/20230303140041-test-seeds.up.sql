
INSERT INTO users (id, email, password, enabled, user_role, api_token)
VALUES
('430c1e15-7913-4df0-984f-e9fcf3c59a56', 'admin@gmail.com',         '123',      true,  'admin'::user_role, 'affe35fd-fbfc-49dd-b048-45a1b1727243'),
('5086a0a4-d5cd-4ca2-89b5-8c61c454cb91', 'admin1-team1@gmail.com',  '456',      true,  'user'::user_role,  '3501c0c1-04b8-4e87-9d80-11728c216fc9'),
('d9530872-2958-4784-bad7-1feaa984d53e', 'admin2-team1@gmail.com',  'ehqe5g',   true,  'user'::user_role,  '85821c21-cba6-40b2-b40f-ed5e46a14271'),
('3c77ad3d-cc95-48ba-b09f-e513806cd843', 'editor1-team1@gmail.com', '34g3g',    true,  'user'::user_role,  '9115ba59-cc38-4bdd-a022-e8992ab7db8d'),
('bf2c27eb-43d4-4158-b9ba-90d5411a7a50', 'viewer1-team1@gmail.com', 'sevaerf',  true,  'user'::user_role,  '0b12eba2-8b6c-43bf-843f-113a672295c0'),
('e19d2890-161b-458f-9fbb-56138712f700', 'admin1-team2@gmail.com',  'tujty',    true,  'user'::user_role,  '917988d8-1850-4d63-887f-99c0202288b9'),
('039e86f7-8c7a-4431-b54e-efb4ebd19559', 'admin2-team2@gmail.com',  'lkwd',     false, 'user'::user_role,  '1ca38e7b-8835-4215-ad5a-f349652aa217'),
('d10b4118-e480-45f7-801b-e3ae67a3bbcb', 'editor1-team2@gmail.com', '34tcse',   true,  'user'::user_role,  '8ecfa449-f54b-4049-af74-9eabe107842f'),
('b8dc93c0-f091-48ce-98e1-29b438e46fa8', 'editor2-team2@gmail.com', '78biyh',   false, 'user'::user_role,  'b5bbadb4-048e-4696-bad1-4641905a3c40'),
('cd3f0f65-f6fe-4c3b-b6c5-406cf66a2a0a', 'viewer1-team2@gmail.com', 'q43tvwhr', true,  'user'::user_role,  '04e517d4-a4ae-4264-b326-c7812dffb00d');
--;;

INSERT INTO teams (id, name, enabled)
VALUES
('e52eda4c-f0e0-40fd-9fd5-1bd81c174342', 'AdminTeam', true),
('3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'Team1',     true),
('1d80e98c-31b3-4ac3-a255-937905dcd475', 'Team2',     true);
--;;

INSERT INTO team_users (team_id, user_id, team_role)
VALUES
('e52eda4c-f0e0-40fd-9fd5-1bd81c174342', '430c1e15-7913-4df0-984f-e9fcf3c59a56', 'team-admin'::team_role),
('3c033fb5-162c-4bb7-b8ea-8a4a26019409', '5086a0a4-d5cd-4ca2-89b5-8c61c454cb91', 'team-admin'::team_role),
('3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'd9530872-2958-4784-bad7-1feaa984d53e', 'team-admin'::team_role),
('3c033fb5-162c-4bb7-b8ea-8a4a26019409', '3c77ad3d-cc95-48ba-b09f-e513806cd843', 'team-editor'::team_role),
('3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'bf2c27eb-43d4-4158-b9ba-90d5411a7a50', 'team-viewer'::team_role),
('1d80e98c-31b3-4ac3-a255-937905dcd475', 'e19d2890-161b-458f-9fbb-56138712f700', 'team-admin'::team_role),
('1d80e98c-31b3-4ac3-a255-937905dcd475', '039e86f7-8c7a-4431-b54e-efb4ebd19559', 'team-admin'::team_role),
('1d80e98c-31b3-4ac3-a255-937905dcd475', 'd10b4118-e480-45f7-801b-e3ae67a3bbcb', 'team-editor'::team_role),
('1d80e98c-31b3-4ac3-a255-937905dcd475', 'b8dc93c0-f091-48ce-98e1-29b438e46fa8', 'team-editor'::team_role),
('1d80e98c-31b3-4ac3-a255-937905dcd475', 'cd3f0f65-f6fe-4c3b-b6c5-406cf66a2a0a', 'team-viewer'::team_role);
--;;

INSERT INTO tickets (id, team_id, name, resolved)
VALUES
('95ebd9fb-9615-480b-9b48-d8d45139ba25', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'Note A dissapeared',    true),
('01dd2480-7df4-4985-b0ac-c7a9471c8b5f', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'Confirm button broken', false),
('d3266425-5d4c-4afc-95ba-a1e0d3b74e85', '1d80e98c-31b3-4ac3-a255-937905dcd475', 'Error 401',             false);
--;;

INSERT INTO ticket_messages (id, ticket_id, user_id, message)
VALUES
('fae7eda4-2dad-4c89-a9b3-300d553401d5', '95ebd9fb-9615-480b-9b48-d8d45139ba25', '5086a0a4-d5cd-4ca2-89b5-8c61c454cb91', 'hello the note is missing'),
('4a379ae8-a8b3-4f84-a3c0-2b1dbaa8a16e', '95ebd9fb-9615-480b-9b48-d8d45139ba25', '430c1e15-7913-4df0-984f-e9fcf3c59a56', 'please describe more...'),
('be3493b0-d366-46a9-af9d-375a950622dd', '95ebd9fb-9615-480b-9b48-d8d45139ba25', '5086a0a4-d5cd-4ca2-89b5-8c61c454cb91', 'It dissapeared when...'),
('0cd36932-1415-4e66-93e2-48fe3835d484', '01dd2480-7df4-4985-b0ac-c7a9471c8b5f', 'd9530872-2958-4784-bad7-1feaa984d53e', 'Can somebody help me?'),
('ecf08d25-5e26-49bb-8e86-0ffde24b8639', '01dd2480-7df4-4985-b0ac-c7a9471c8b5f', '430c1e15-7913-4df0-984f-e9fcf3c59a56', 'Hello.. tell me what happened'),
('b0483035-15a5-4930-bc50-b7e250445afe', 'd3266425-5d4c-4afc-95ba-a1e0d3b74e85', 'e19d2890-161b-458f-9fbb-56138712f700', 'Settings page is blank...');
--;;

INSERT INTO notes (id, team_id, is_public, name, content)
VALUES
('d73d683a-a0bf-43bf-a1dd-0a103b905223', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', false, 'Planning meeting',   'Monday 10am sales team'),
('3c3ac540-0ea6-484a-8772-638a51e2dd80', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', false, 'Reminder',           'Send emails'),
('7dc83523-835e-4663-b7dc-12a4d65a7d79', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', false, 'Onboarding process', 'Give access, password and repo'),
('cd3beda4-455a-4140-90cd-74fc44d2a1c9', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', true,  'Birthdays',          '10-10 Sara, 12-03 Jhon'),
('ae8c2b30-266d-4f40-a107-667db1f3c4d7', '1d80e98c-31b3-4ac3-a255-937905dcd475', false, 'Goal',               'Deliver product'),
('95e6eb6f-3eaf-4f7c-8dc7-04140c56dc56', '1d80e98c-31b3-4ac3-a255-937905dcd475', false, 'Next event',         'Demo presentation'),
('322a1121-1afe-4cb1-aa77-72c672291de9', '1d80e98c-31b3-4ac3-a255-937905dcd475', false, 'Documentation',      'link1 link2 link3');
--;;

INSERT INTO billing_details (id, team_id, company_name, address, vat)
VALUES
('9143ad55-bef9-4857-938e-3e12c869f084', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'Company 1', 'New york 123', 'dfkjn23e'),
('b36feea7-845d-47e4-a652-f44b2654f475', '1d80e98c-31b3-4ac3-a255-937905dcd475', 'Company 2', 'Paris 543', 'sdoi3');
--;;

INSERT INTO plans (id, name, price_usd, price_per_user, public, team_id, max_notes, max_chars, max_users)
VALUES
('ebb23b9b-f967-4d20-8f0d-78e40a0bc31a', 'Admin plan', 0, false, false, 'e52eda4c-f0e0-40fd-9fd5-1bd81c174342', 10, 50, 3),
('be9c8fc7-949a-4e58-9eaa-afdd909eb655', 'Team1 basic plan', 10, false, false, '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 3, 20, 3),
('8ba9588c-dbf8-4a6b-9870-ef830df416b1', 'Team1 pro plan', 20, false, false, '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 50, 200, 10),
('932318e4-d8e4-4775-9ca5-5f40c19524b4', 'Team1 public plan', 5, false, true, '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 5, 50, 5),
('66c1ffd7-b553-43d2-a6b0-fcc337e5ed0f', 'Team2 basic plan', 5, false, false, '1d80e98c-31b3-4ac3-a255-937905dcd475', 10, 100, 5),
('d1151de8-08d8-4354-8aef-97b4b2584b5e', 'Team2 per user plan', 1, true, false, '1d80e98c-31b3-4ac3-a255-937905dcd475', 10, 100, 10);
--;;

INSERT INTO subscriptions (id, team_id, plan_id, canceled)
VALUES
('591a3740-082d-4895-a690-6bb227dca3c3', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 'be9c8fc7-949a-4e58-9eaa-afdd909eb655', false),
('086450b9-149a-4680-982d-fd249dd7b497', '1d80e98c-31b3-4ac3-a255-937905dcd475', '66c1ffd7-b553-43d2-a6b0-fcc337e5ed0f', false);
--;;

INSERT INTO invoices (id, team_id, amount_usd, subscription_id)
VALUES
('50885473-2078-4f59-9840-ea73e3ab0f2e', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 10, '591a3740-082d-4895-a690-6bb227dca3c3'),
('3337caf9-5dfb-4fb0-ae3c-23b137e3fd67', '3c033fb5-162c-4bb7-b8ea-8a4a26019409', 10, '591a3740-082d-4895-a690-6bb227dca3c3'),
('d4d88710-d5e7-42a0-82e5-278e4f58cd76', '1d80e98c-31b3-4ac3-a255-937905dcd475', 5,  '086450b9-149a-4680-982d-fd249dd7b497');
--;;
