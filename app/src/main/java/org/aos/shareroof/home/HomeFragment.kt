package org.aos.shareroof.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnMapReadyCallback,  GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mView: MapView
    // 현재 위치를 검색하기 위함
    private lateinit var fusedLocationClient: FusedLocationProviderClient // 위칫값 사용
    private lateinit var locationCallback: LocationCallback // 위칫값 요청에 대한 갱신 정보를 받아옴
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    val data  = arrayOf(arrayOf("0", "대한민국 충청남도 예산군 오촌리 한옥", "36.72025330080758", "126.80161084451493", "대한민국 충청남도 예산군 오촌리 한옥", "남자", "단독주택", "94", "212,808", "No", "2", "No", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
        arrayOf("1", "대한민국 경상북도 예천군 미석리 단독주택", "36.74657477478244", "128.56223004733508", "대한민국 경상북도 예천군 미석리 단독주택", "남자", "기타", "28", "65,062", "No", "4", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("2", "대한민국 경상북도 성주군 자양리 진달래아파트 103동 1104호", "35.95638732965905", "128.24250824396978", "대한민국 경상북도 성주군 자양리 진달래아파트 103동 1104호", "남자", "단독주택", "27", "138,257", "Yes", "2", "Yes", "도시 외곽의 아파트입니다. 다양한 문화시설과 정거장이 존재하고, 조용하고, 주차시설도 완비되어 있습니다. "),
        arrayOf("3", "대한민국 경상북도 봉화군 설매리 단독주택", "36.831400821355416", "128.72396228670647", "대한민국 경상북도 봉화군 설매리 단독주택", "여자", "단독주택", "69", "70,484", "Yes", "1", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("4", "대한민국 경기도 용인시 수지구 단독주택", "37.34812949352725", "127.07199945464953", "대한민국 경기도 용인시 수지구 단독주택", "여자", "단독주택", "98", "66,418", "No", "4", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("5", "대한민국 충청북도 음성군 병암리 단독주택", "37.02107496203536", "127.59326716553706", "대한민국 충청북도 음성군 병암리 단독주택", "남자", "단독주택", "19", "158,589", "Yes", "2", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("6", "대한민국 충청북도 영동군 돈대리 해바라기아파트 101동 1113호", "36.16717441040339", "127.90039910025969", "대한민국 충청북도 영동군 돈대리 해바라기아파트 101동 1113호", "여자", "아파트", "70", "207,386", "Yes", "4", "Yes", "도시 외곽의 아파트입니다. 다양한 문화시설과 정거장이 존재하고, 조용하고, 주차시설도 완비되어 있습니다. "),
        arrayOf("7", "대한민국 경상북도 예천군 무지리 단독주택", "36.60702251885442", "128.3173711393743", "대한민국 경상북도 예천군 무지리 단독주택", "여자", "단독주택", "84", "174,855", "No", "3", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("8", "대한민국 경상북도 울진군 후포리 단독주택", "36.6656738270248", "129.47205961406294", "대한민국 경상북도 울진군 후포리 단독주택", "여자", "단독주택", "22", "131,480", "No", "4", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("9", "대한민국 경상북도 울진군 삼근리 쌍용아파트 108동 1114호", "36.89720350991671", "129.26793055516654", "대한민국 경상북도 울진군 삼근리 쌍용아파트 108동 1114호", "여자", "기타", "87", "145,035", "No", "4", "Yes", "도시 외곽의 아파트입니다. 다양한 문화시설과 정거장이 존재하고, 조용하고, 주차시설도 완비되어 있습니다. "),
        arrayOf("10", "대한민국 경상남도 함양군 원산리 단독주택", "35.582343688323746", "127.68018492793648", "대한민국 경상남도 함양군 원산리 단독주택", "남자", "단독주택", "57", "206,031", "No", "2", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("11", "대한민국 전라남도 구례군 중대리 한옥", "35.156635680941505", "127.59268221121975", "대한민국 전라남도 구례군 중대리 한옥", "여자", "아파트", "79", "132,836", "Yes", "2", "No", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
        arrayOf("12", "대한민국 충청북도 단양군 연곡리 단독주택", "37.106720820717825", "128.2989316435201", "대한민국 충청북도 단양군 연곡리 단독주택", "남자", "아파트", "69", "159,945", "Yes", "2", "No", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("13", "대한민국 전라남도 보성군 오봉리 한옥", "34.73907067215209", "127.20370797111568", "대한민국 전라남도 보성군 오봉리 한옥", "남자", "기타", "39", "85,394", "No", "3", "No", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
        arrayOf("14", "대한민국 경상북도 청송군 개일리 단독주택", "36.262363428781384", "129.0312439598046", "대한민국 경상북도 청송군 개일리 단독주택", "여자", "기타", "53", "85,394", "No", "2", "Yes", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("15", "대한민국 경상남도 함안군 춘곡리 행복아파트 101동 1115호", "35.24998173504081", "128.37394783495535", "대한민국 경상남도 함안군 춘곡리 행복아파트 101동 1115호", "남자", "기타", "79", "112,504", "No", "3", "Yes", "도시 외곽의 아파트입니다. 다양한 문화시설과 정거장이 존재하고, 조용하고, 주차시설도 완비되어 있습니다. "),
        arrayOf("16", "대한민국 경상북도 포항시 석계리 한옥", "36.237034588242196", "129.14858126655517", "대한민국 경상북도 포항시 석계리 한옥", "남자", "단독주택", "11", "215,519", "No", "1", "Yes", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
        arrayOf("17", "대한민국 강원도 인제군 용대리 한옥", "38.192816151844305", "128.38334984873745", "대한민국 강원도 인제군 용대리 한옥", "여자", "기타", "34", "229,074", "Yes", "4", "Yes", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
        arrayOf("18", "대한민국 경상남도 양산시 외석리 단독주택", "35.453847571813085", "129.05195337778812", "대한민국 경상남도 양산시 외석리 단독주택", "남자", "아파트", "26", "146,390", "Yes", "1", "No", "도시 한가운데 있는 한옥의 문을 열고 들어가면 빌딩 숲은 온데간데 사라지고 완전히 다른 풍경이 펼쳐집니다. 이곳에서 마치 살아보는 것처럼 일상을 누려보면 서울은 어느새 완전히 새로운 곳이 됩니다. 에어비앤비·서울관광재단과 함께 일상을 여행처럼, 여행은 일상처럼 한옥에서 살아보는 즐거움을 느껴보세요"),
        arrayOf("19", "대한민국 경상북도 포항시 남구 한옥", "35.98373741960251", "129.55533955410388", "대한민국 경상북도 포항시 남구 한옥", "여자", "단독주택", "57", "199,254", "No", "1", "No", "도심 한 가운데 있는 현대적이고 실용적인 단독주택입니다. 최대 4인까지 편안하고 실용적인 여행이 가능하고, 주변 맛집들과 산책길 잔디 위 소풍을 즐길 수 있는 숙소로, 작은 마당에서 맥주 한잔 마실 수 있는 여유와 편안함을 제공합니다."),
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)

        requirePermissions(permissions, 999) //권환 요쳥, 999는 임의의 숫자
        mView.onCreate(savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.detailBottom.bottomView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        setListeners()
    }

    private fun setListeners() {
        binding.ivMypage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
        }
        binding.ivRank.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rankFragment)
        }
    }

    private fun setBottomSheet(position:Int) {
        binding.detailBottom.wbDetail.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        Log.d("TAG", "setBottomSheet: ${position}")
        binding.detailBottom.wbDetail.loadUrl("https://shareroof.netlify.app/houses/${position}")

        bottomSheetBehavior = BottomSheetBehavior.from(binding.detailBottom.bottomView)
        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            isDraggable = true
        }

    }

    override fun onStart() {
        super.onStart()
        mView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mView.onPause()
    }

    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            val isAllPermissionsGranted = permissions.all { checkSelfPermission(requireContext(),it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    // 권한이 있는 경우 실행
    fun permissionGranted(requestCode: Int) {
        startProcess() // 권한이 있는 경우 구글 지도를준비하는 코드 실행
    }

    // 권한이 없는 경우 실행
    fun permissionDenied(requestCode: Int) {
        Toast.makeText(context
            , "권한 승인이 필요합니다."
            , Toast.LENGTH_LONG)
            .show()
    }

    fun startProcess() {
        mView = binding.mapView
        mView.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setOnMarkerClickListener(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        updateLocation()
    }

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.let {
                    for(location in it.locations) {
                        Log.d("Location", "${location.latitude} , ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setLastLocation(lastLocation: Location) {

      /*  val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15f)
            .build()*/
        data.forEach { data ->
            val latlng = LatLng(data[2].toDouble(), data[3].toDouble())
            val bitmapdraw = resources.getDrawable(R.drawable.ic_marker) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, 80, 110, false)
            val markerOptions = MarkerOptions()
                .position(latlng)
                .title(data[0])
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            mMap.addMarker(markerOptions)
        }



    }

    override fun onMarkerClick(marker: Marker): Boolean {
        setBottomSheet(marker.title!!.toInt())
        return true
    }

}