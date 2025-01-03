INSERT INTO students (name, kana, nickname, email, address, age, gender, remark, is_deleted)
VALUES
  ('佐藤太郎', 'サトウタロウ', 'たろたん', 'taro@example.com', '東京都', 20, '男', '特になし', 0),
  ('鈴木次郎', 'スズキジロウ', 'じろう', 'jiro.suzuki@example.com', '大阪府', 28, '男', '特になし', 0),
  ('田中花子', 'タナカハナコ', 'はなこ', 'hanako.tanaka@example.com', '愛知県', 25, '女', '特になし', 0),
  ('高橋美咲', 'タカハシミサキ', 'みさき', 'misaki.takahashi@example.com', '北海道', 32, '女', '特になし', 0),
  ('山田健', 'ヤマダケン', 'けん', 'ken.yamada@example.com', '福岡県', 27, 'その他', '特になし', 0),
  ('渡辺直美', 'ワタナベナオミ', 'ナオミ', 'naomi@example.com', 'アメリカ', 36, '女', '特になし', 0);

INSERT INTO students_courses (student_id, course, start_at, complete_at)
VALUES
  (1, 'ピアノコース', '2024-01-01 00:00:00', '2025-01-01 00:00:00'),
  (2, 'ギターコース', '2024-02-01 00:00:00', '2025-02-01 00:00:00'),
  (3, 'ドラムコース', '2024-03-01 00:00:00', '2025-03-01 00:00:00'),
  (4, 'ヴァイオリンコース', '2024-04-01 00:00:00', '2025-04-01 00:00:00'),
  (5, 'サックスコース', '2024-05-01 00:00:00', '2025-05-01 00:00:00'),
  (1, 'ボーカルコース', '2024-06-01 00:00:00', '2025-06-01 00:00:00'),
  (2, 'ピアノコース', '2024-07-01 00:00:00', '2025-07-01 00:00:00'),
  (3, 'ギターコース', '2024-08-01 00:00:00', '2025-08-01 00:00:00'),
  (4, 'ドラムコース', '2024-09-01 00:00:00', '2025-09-01 00:00:00'),
  (5, 'ヴァイオリンコース', '2024-10-01 00:00:00', '2025-10-01 00:00:00'),
  (6, 'サックスコース', '2024-11-01 00:00:00', '2025-11-01 00:00:00'),
  (6, 'ボーカルコース', '2024-12-01 00:00:00', '2025-12-01 00:00:00');
