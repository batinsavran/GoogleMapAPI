# Business Locator App

Business Locator App, kullanıcıların harita üzerinde belirli işletmeleri görüntüleyebileceği, filtreleyebileceği ve ayrıntılarını inceleyebileceği bir Android uygulamasıdır. Uygulama, Google Maps API kullanarak kullanıcıların konumlarını belirler ve yakınlarındaki işletmeleri gösterir.

## Özellikler

- Kullanıcının mevcut konumunu belirleme ve haritada gösterme
- Belirli bir yarıçap içinde işletmeleri gösterme
- İşletmeleri mutfak tipine göre filtreleme
- İşletme detaylarını gösterme
- İşletmeleri belirli bir zoom seviyesinde gösterme/kaldırma

## Gereksinimler

- Android Studio
- Google Maps API Anahtarı

## Kurulum

1. **Google Maps API Anahtarı Alın**

   - Google Cloud Console üzerinden bir proje oluşturun.
   - Google Maps Android API ve Google Places API'yi etkinleştirin.
   - API anahtarınızı alın.

2. **API Anahtarını Projeye Ekleyin**

   - `local.properties` dosyasına aşağıdaki satırı ekleyin:
     ```
     MAPS_API_KEY=YOUR_API_KEY_HERE
     ```

3. **Projeyi Kopyalayın ve Yapılandırın**

   - Bu projeyi yerel makinenize kopyalayın:
     ```bash
     git clone https://github.com/kullanici-adi/business-locator-app.git
     cd business-locator-app
     ```

4. **Gerekli İzinleri Ekleyin**

   - `AndroidManifest.xml` dosyasına gerekli izinleri ekleyin:
     ```xml
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     ```

5. **Maps API Anahtarını Manifest Dosyasına Ekleyin**

   - `AndroidManifest.xml` dosyasına API anahtarınızı ekleyin:
     ```xml
     <application>
         ...
         <meta-data
             android:name="com.google.android.geo.API_KEY"
             android:value="${MAPS_API_KEY}" />
     </application>
     ```

## Kullanım

1. **Uygulamayı Başlatın**

   - Android Studio'da projeyi açın ve bir cihazda veya emülatörde çalıştırın.

2. **Kullanıcı Konumunu Belirleyin**

   - Uygulama açıldığında, kullanıcı konumunu belirler ve haritada gösterir.

3. **İşletmeleri Görüntüleyin ve Filtreleyin**

   - Haritada işletmeleri görebilir, işletme detaylarını inceleyebilir ve filtreleme seçenekleri ile işletmeleri mutfak tipine göre filtreleyebilirsiniz.


