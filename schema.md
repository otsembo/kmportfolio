### **Database Schema**

#### **Users Table**

- **Table Name:** `users`

| Column       | Type          | Description                           |
|--------------|---------------|---------------------------------------|
| `id`          | `INTEGER`      | Primary Key, Auto-increment            |
| `username`    | `VARCHAR(255)` | Unique, Username of the user           |
| `email`       | `VARCHAR(255)` | Unique, Email address of the user      |
| `password_hash`| `VARCHAR(255)` | Hashed password for authentication     |
| `created_at`  | `TIMESTAMP`    | Record creation timestamp              |
| `updated_at`  | `TIMESTAMP`    | Record last update timestamp           |

#### **Projects Table**

- **Table Name:** `projects`

| Column       | Type          | Description                           |
|--------------|---------------|---------------------------------------|
| `id`          | `INTEGER`      | Primary Key, Auto-increment            |
| `title`       | `VARCHAR(255)` | Title of the project                   |
| `description` | `TEXT`         | Detailed description of the project    |
| `url`         | `VARCHAR(255)` | Link to the project                    |
| `created_at`  | `TIMESTAMP`    | Record creation timestamp              |
| `updated_at`  | `TIMESTAMP`    | Record last update timestamp           |

#### **Blogs Table**

- **Table Name:** `blogs`

| Column       | Type          | Description                           |
|--------------|---------------|---------------------------------------|
| `id`          | `INTEGER`      | Primary Key, Auto-increment            |
| `title`       | `VARCHAR(255)` | Title of the blog post                 |
| `content`     | `TEXT`         | Content of the blog post (Rich Text)   |
| `created_at`  | `TIMESTAMP`    | Record creation timestamp              |
| `updated_at`  | `TIMESTAMP`    | Record last update timestamp           |

#### **Comments Table**

- **Table Name:** `comments`

| Column       | Type          | Description                           |
|--------------|---------------|---------------------------------------|
| `id`          | `INTEGER`      | Primary Key, Auto-increment            |
| `blog_id`     | `INTEGER`      | Foreign Key, References `blogs(id)`    |
| `author`      | `VARCHAR(255)` | Name of the comment author             |
| `content`     | `TEXT`         | Content of the comment                 |
| `created_at`  | `TIMESTAMP`    | Record creation timestamp              |
| `updated_at`  | `TIMESTAMP`    | Record last update timestamp           |

### **Relationships**

- **Users** have many **Projects** (1-to-many)
- **Users** have many **Blogs** (1-to-many)
- **Blogs** have many **Comments** (1-to-many)
