import MuiBottomNavigation from '@mui/material/BottomNavigation'
import BottomNavigationAction from '@mui/material/BottomNavigationAction'
import RestoreIcon from '@mui/icons-material/Restore'
import FavoriteIcon from '@mui/icons-material/Favorite'
import LocationOnIcon from '@mui/icons-material/LocationOn'
import Paper from '@mui/material/Paper'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useTheme } from '@emotion/react'
import { styled } from '@mui/material/styles'

const StyledBottomNavigationAction = styled(BottomNavigationAction)(({ theme }) => ({
  color: 'inherit',
}))

const BottomNavigation = ({ inputState, setInputState, handleBackNavigation}) => {
  const theme = useTheme()
  return (
    <Paper
      sx={{
        borderRadius: 1,
        mt: 2,
        backgroundColor: theme.palette.primary.main,
        color: "white",
        overflow: "hidden",
      }}
      elevation={5}
    >
      <MuiBottomNavigation
        sx={{
          backgroundColor: "inherit",
          color: "inherit",
        }}
      >
        <StyledBottomNavigationAction
          label="Back"
          icon={<ArrowBackIcon />}
          onClick={handleBackNavigation}
        />
        <StyledBottomNavigationAction
          label="Favorites"
          icon={<FavoriteIcon />}
        />
        <StyledBottomNavigationAction
          label="Recents"
          icon={<RestoreIcon/>}
        />
      </MuiBottomNavigation>
    </Paper>
  )
}

export default BottomNavigation