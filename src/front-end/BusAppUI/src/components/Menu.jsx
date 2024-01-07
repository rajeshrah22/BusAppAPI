//React
import React, { useEffect } from 'react'

//MUI
import { styled } from '@mui/material/styles'
import { grey } from '@mui/material/colors'
import Box from '@mui/material/Box'
import Drawer from '@mui/material/SwipeableDrawer'
import Skeleton from '@mui/material/Skeleton'
import DragHandleIcon from '@mui/icons-material/DragHandle'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemText from '@mui/material/ListItemText'
import ListItemAvatar from '@mui/material/ListItemAvatar'
import Avatar from '@mui/material/Avatar'
import ImageIcon from '@mui/icons-material/Image'
import { useTheme } from '@emotion/react'
import TextField from '@mui/material/TextField'
import InputAdornment from '@mui/material/InputAdornment'
import SearchIcon from '@mui/icons-material/Search'
import Paper from '@mui/material/Paper'

const AgencyLink = ({agencyTag, location, handleClick}) => {
  return (
    <>
      <Paper
        elevation={1}
        sx={{
          my: 1,
        }}
      >
        <ListItemButton
          sx={{
            borderRadius: 1,
            width: '100%',
          }}
        >
          <ListItemAvatar>
            <Avatar>
              <ImageIcon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText primary={agencyTag} secondary={location}></ListItemText>
        </ListItemButton>
      </Paper>
    </>
  )
}

const MenuList = ({ list }) => {
  return (
    <>
      <List>
        {
          list.map((agency, index) => {
            return (
              <AgencyLink
                agencyTag={agency.agencyTag}
                location={agency.location}
                key={index}
              />
            )
          })
        }
      </List>
    </>
  )
}

const SearchBox = () => {
  return (
    <TextField
          sx={{
            mb: 2, // Add some bottom margin to separate it from the DragHandleIcon
            width: '100%', // Adjust as needed
            px: 2,
          }}
          variant="outlined"
          placeholder="Search"
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
    />
  )
}

const agencies = [
  {
    "agencyTag": "yessir",
    "location": "Westborough"
  },
  {
    "agencyTag": "yeah",
    "location": "New York"
  },
  {
    "agencyTag": "Malborough",
    "location": "Manchester"
  }
]

const Menu = () => {
  const [open, setOpen] = React.useState(false)

  const toggleDrawer = (open) => {
    setOpen(open)
  }

  //prop constants
  const ANCHOR = 'bottom'

  //style constants
  const DRAWER_BLEEDING = 103
  const ICON_WIDTH = '16px'
  const DRAWER_HEIGHT = '75%'


  const theme = useTheme()

  return (
    <>
      <Drawer
        sx={{ 
          backgroundColor: 'white',
          '.MuiPaper-root': {
            // borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            height: `calc(${DRAWER_HEIGHT} - ${DRAWER_BLEEDING}px)`,
            overflow: 'visible'
          }
        }}
        swipeAreaWidth={DRAWER_BLEEDING}
        anchor={ANCHOR}
        open={open}
        onClose={() => {toggleDrawer(false)}}
        onOpen={() => {toggleDrawer(true)}}
      >
        <Box
          sx={{
            border: 'none',
            backgroundColor: 'white',
            position: 'absolute',
            top: -DRAWER_BLEEDING,
            visibility: 'visible',
            borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            right: 0,
            left: 0,
            elevation: 0,
          }}
        >
          <DragHandleIcon
            sx={{
            position: 'relative', left: `calc(50% - ${ICON_WIDTH} / 2)`,
            }}
          >
          </DragHandleIcon>
          <SearchBox/>
        </Box>
        <Box
          sx={{
            px: 2,
            py: 2,
            height: '100%',
            overflow: 'auto',
          }}
        >
          <Box
            sx={{
              borderRadius: 1,
              // border: `1px solid ${theme.palette.grey.A400}`,
              border: 'none',
              height: '100%',
            }}
          >
            <MenuList list={agencies}/>
          </Box>
        </Box>
      </Drawer>
    </>
  )
}

export default Menu