project-root/
│
├── public/                  # Thư mục chứa các file tĩnh, không qua build process
│   ├── index.html           # File HTML chính
│   ├── favicon.ico          # Favicon
│   └── assets/              # Các assets tĩnh (images, fonts... không cần xử lý)
│
├── src/                     # Source code chính của ứng dụng
│   ├── assets/              # Assets cần xử lý qua webpack (images, fonts, scss...)
│   ├── components/         # Các component UI dùng chung (Button, Card, Modal...)
│   │   └── Button/
│   │       ├── Button.jsx
│   │       ├── Button.module.css
│   │       └── index.js     # Export component
│   │
│   ├── pages/              # Các page/route của ứng dụng
│   │   ├── Home/
│   │   │   ├── Home.jsx
│   │   │   ├── Home.module.css
│   │   │   └── components/  # Components riêng của trang Home
│   │   └── About/
│   │
│   ├── layouts/            # Các layout chung (MainLayout, AuthLayout...)
│   ├── hooks/              # Custom hooks
│   ├── contexts/           # React contexts
│   ├── utils/              # Các utility/helper functions
│   ├── services/           # API services (axios instances, API calls...)
│   ├── store/              # Redux store (nếu dùng Redux)
│   │   ├── slices/         # Redux slices
│   │   └── store.js
│   ├── router/             # Cấu hình routing (React Router)
│   ├── constants/          # Các constant values
│   ├── styles/             # Global styles, theme, variables...
│   ├── App.jsx             # Root component
│   └── index.jsx           # Entry point
│
├── .gitignore              # Git ignore file
├── package.json            # Project metadata và dependencies
├── README.md               # Project documentation
└── jsconfig.json           # JS configuration (alias paths...)


### Giải thích chi tiết:

1. **public/** :

* Chứa các file tĩnh không qua build process
* `index.html` là file HTML chính, React sẽ render vào div#root

1. **src/** :

* **assets/** : Hình ảnh, fonts, file SCSS/SASS...
* **components/** : Các component UI tái sử dụng, tổ chức theo từng component con
* **pages/** : Mỗi thư mục đại diện cho một route/page
* **hooks/** : Custom hooks (useFetch, useLocalStorage...)
* **services/** : Xử lý API calls, axios configuration
* **store/** : Redux store (nếu sử dụng Redux)

1. **Tổ chức component** :

* Mỗi component có riêng file JSX, styles (CSS Module) và file index.js để export
* Ví dụ: `Button/Button.jsx`, `Button/Button.module.css`

1. **Cấu trúc mở rộng cho dự án lớn** :

* Có thể thêm thư mục `features/` thay cho `pages/` nếu dùng Redux Toolkit
* Thêm `tests/` cho unit tests
* Thêm `docker/` nếu dùng Docker

### Lưu ý:

* Cấu trúc có thể điều chỉnh tùy theo quy mô dự án
* Với dự án dùng TypeScript, thay đổi extension từ `.jsx` sang `.tsx`
* Có thể cấu hình alias path trong `jsconfig.json` để import ngắn gọn hơn

### Giải thích chi tiết:

1. **public/** :

* Chứa các file tĩnh không qua build process
* `index.html` là file HTML chính, React sẽ render vào div#root

1. **src/** :

* **assets/** : Hình ảnh, fonts, file SCSS/SASS...
* **components/** : Các component UI tái sử dụng, tổ chức theo từng component con
* **pages/** : Mỗi thư mục đại diện cho một route/page
* **hooks/** : Custom hooks (useFetch, useLocalStorage...)
* **services/** : Xử lý API calls, axios configuration
* **store/** : Redux store (nếu sử dụng Redux)

1. **Tổ chức component** :

* Mỗi component có riêng file JSX, styles (CSS Module) và file index.js để export
* Ví dụ: `Button/Button.jsx`, `Button/Button.module.css`

1. **Cấu trúc mở rộng cho dự án lớn** :

* Có thể thêm thư mục `features/` thay cho `pages/` nếu dùng Redux Toolkit
* Thêm `tests/` cho unit tests
* Thêm `docker/` nếu dùng Docker

### Lưu ý:

* Cấu trúc có thể điều chỉnh tùy theo quy mô dự án
* Với dự án dùng TypeScript, thay đổi extension từ `.jsx` sang `.tsx`
* Có thể cấu hình alias path trong `jsconfig.json` để import ngắn gọn hơn
