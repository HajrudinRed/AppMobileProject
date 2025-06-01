package  com.example.appquizlizard.ui.theme.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.appquizlizard.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily.Companion.Default
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit




@Preview(showSystemUi = true)
@Composable
fun IntroScreenPreview(){
    IntroScreen()
}


@Composable
fun IntroScreen (){
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFFFF"))


    val shape = RoundedCornerShape(
        topStart = CornerSize(0.dp), // Top-left corner
        topEnd = CornerSize(0.dp),   // Top-right corner
        bottomStart = CornerSize(50.dp), // Bottom-left corner
        bottomEnd = CornerSize(50.dp)    // Bottom-right corner
    )


    //Column
    Column(
        modifier = Modifier
            .padding(0.dp)
            .background(color = backgroundColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        //Yellow top box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape) // Apply the shape here
                .background(Color(0xFF800020)) // Yellow color
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ){
            Text(// ---------------> HOW DO I CHANGE THIS FONT TO Rounded Mplus 1c Bold
                text = "QuizLizard",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF800020),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.size(width = 0.dp, height = 100.dp))

        // Picture
        Image(
            painter = painterResource(id = R.drawable.quizlizardlogo),
            contentDescription = "Logo Image",
            modifier = Modifier
                .size(225.dp)
        )

        Spacer(modifier = Modifier.size(width = 0.dp, height = 140.dp))

        //GameButton
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(50.dp),
            onClick = {/*TO DO*/},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020))
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.size(width = 0.dp, height = 10.dp))

        //FlagsButton
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(50.dp),
            onClick = {/*TO DO*/},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020))
        ) {
            Text(
                text = "Signup",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}