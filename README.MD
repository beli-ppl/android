# BeLi Android

## A. Deskripsi Umum

![alt text](https://drive.google.com/uc?export=view&id=1ji3Jlm3u6425zcdmgUOPzS9C84O6ZYSh)

BeLi adalah sebuah aplikasi yang menggabungkan platform Android, Arduino, dan Unity dengan tujuan untuk mengidentifikasi kondisi tubuh pengguna, mengevaluasi kondisi kesehatan pengguna, serta menjaga kesehatan para penggunanya dengan memberikan rekomendasi jumlah gerakan yang harus dilakukan penggunanya dalam sehari dan rekomendasi jenis makanan sehat yang dapat mereka makan, berdasarkan kondisi tubuh mereka. BeLi juga dapat memberikan edukasi tentang kesehatan kepada penggunanya dengan menyediakan game interaktif dan dapat memberikan kemudahan kepada pengguna untuk melakukan appointment dengan dokter.

Pada BeLi, Android akan memiliki dua sensor, yaitu sensor detak jantung yang berguna untuk menghitung jumlah detak jantung pengguna per menit dan sensor gyro yang berguna untuk menghitung jumlah langkah yang dilakukan oleh pengguna. Kemudian, Android akan menyimpan data - data tersebut ke dalam database sebagai data kondisi tubuh pengguna.

Arduino pun juga memiliki sensor temperatur untuk mengukur suhu tubuh pengguna dan menyimpannya ke dakomunikasitabase sebagai data kondisi tubuh pengguna. Arduino juga memiliki sensor kamera untuk memindai QR Code yang berfungsi untuk membuat appointment dengan dokter . Data - data dari Android dan Arduino tersebut pun akan diintegrasikan dan menghasilkan output saran jumlah langkah yang harus dilakukan oleh pengguna dalam sehari.

Pada sistem ini, Unity Engine berfungsi untuk memberikan game - game interaktif tentang kesehatan untuk memberikan informasi tentang kesehatan dan memberikan wawasan lebih kepada masyarakat tentang kesehatan.

## B. Development Aplikasi

### Prerequisites
1. [Install Latest Java SDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Install Latest Android SDK](https://developer.android.com/studio/install)
3. [Install Apache Ant](http://ant.apache.org/)

### Install Aplikasi
1. Jalankan command `./gradlew installDebug` untuk menginstall apk pada emulator ataupun device yang terhubung dengan komputer.

## C. Penggunaan Aplikasi
[Video Penggunaan Aplikasi](https://www.youtube/com)

## D. Deliverable
[Proposal](https://drive.google.com/file/d/1Y7Ux3YUFwZuOxJE7uHQxdJ02Y3zDQnk1/view?usp=sharing) <br />
[Laporan](https://docs.google.com/document/d/1QJjzUlu202ThwhrDmBorzTN7u4u27Eb1zSuiVLmP--w/edit?usp=sharing) <br />